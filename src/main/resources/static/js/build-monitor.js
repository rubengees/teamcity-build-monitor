var mainContainer = document.getElementById("main");
var lastData = "";

pollTeamcityStatus();

function pollTeamcityStatus() {
    $.getJSON("/teamcityStatus")
        .done(function (data) {
            var dataAsString = JSON.stringify(data);

            if (dataAsString !== lastData) {
                lastData = dataAsString;
            } else {
                return;
            }

            if (data.error) {
                throw data;
            }

            clearLayout();

            data.projectStates.forEach(function (projectState) {
                var buildItem = constructBuildItem(projectState);

                mainContainer.appendChild(buildItem);
            })
        })
        .fail(function (jqxhr, textStatus) {
            if (jqxhr.responseJSON) {
                textStatus = jqxhr.responseJSON.error + "<br/>"
                    + jqxhr.responseJSON.exception + "<br/>"
                    + jqxhr.responseJSON.message
            } else if (textStatus === 'error') {
                textStatus = 'Could not establish connection to backend'
            }

            clearLayout();

            var container = constructErrorLayout(textStatus);

            mainContainer.appendChild(container);
        })
        .always(function () {
            setTimeout(pollTeamcityStatus, 5000);
        });
}

function clearLayout() {
    timeago.cancel();

    while (mainContainer.firstChild) {
        mainContainer.removeChild(mainContainer.firstChild);
    }
}

function constructBuildItem(projectState) {
    var container = document.createElement("div");
    var centerContainer = document.createElement("div");
    var nameContainer = document.createElement("a");

    centerContainer.style.margin = "auto";
    centerContainer.classList.add("flex-center");

    nameContainer.href = TEAMCITY_URL + "/viewType.html?buildTypeId=" + projectState.id;
    nameContainer.classList.add("big-text");
    nameContainer.textContent += projectState.name;

    centerContainer.appendChild(nameContainer);

    container.classList.add("flex-center", "build-item", "card");
    container.appendChild(centerContainer);

    if (projectState.status === 'SUCCESS') {
        container.classList.add("build-item-success");
    } else if (projectState.status === 'FAILURE' || projectState.status === 'ERROR') {
        container.classList.add("build-item-failure");
    } else {
        container.classList.add("build-item-unknown");
    }

    if (projectState.branchName) {
        var branchNameContainer = document.createElement("div");

        branchNameContainer.textContent += projectState.branchName;

        centerContainer.appendChild(branchNameContainer);
    }

    if (projectState.buildNumber) {
        var buildNumberContainer = document.createElement("div");

        buildNumberContainer.classList.add("absolute-bottom-left");
        buildNumberContainer.textContent += "#" + projectState.buildNumber;

        container.appendChild(buildNumberContainer);
    }

    if (projectState.dateTime) {
        var timeContainer = document.createElement("div");

        timeContainer.classList.add("absolute-bottom-right");
        timeContainer.setAttribute("datetime", projectState.dateTime);
        timeago().render(timeContainer);

        container.appendChild(timeContainer);
    }

    return container;
}

function constructErrorLayout(message) {
    var container = document.createElement("div");

    container.innerHTML = message;

    return container;
}
