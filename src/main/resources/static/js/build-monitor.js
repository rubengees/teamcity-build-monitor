var mainContainer = document.getElementById("main");
var lastData = "";

pollTeamcityStatus();

function pollTeamcityStatus() {
    fetch("/teamcityStatus")
        .then(function (response) {
            return response.text();
        })
        .then(function (data) {
            if (data !== lastData) {
                lastData = data;

                var parsedData = JSON.parse(data);

                if (parsedData.error) {
                    throw parsedData;
                } else {
                    return parsedData;
                }
            } else {
                return null;
            }
        })
        .then(function (data) {
            if (data) {
                clearLayout();

                data.projectStates.forEach(function (projectState) {
                    var buildItem = constructBuildItem(projectState);

                    mainContainer.appendChild(buildItem);
                })
            }
        })
        .catch(function (reason) {
            if (reason) {
                if (reason.error && reason.exception) {
                    reason.message = reason.error + "<br/>" + reason.exception + "<br/>" + reason.message
                }

                clearLayout();

                var container = constructErrorLayout(reason);

                mainContainer.appendChild(container);
            }
        })
        .finally(function () {
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

function constructErrorLayout(reason) {
    var container = document.createElement("div");

    container.innerHTML = reason.message;

    return container;
}
