var mainContainer = document.getElementById("main");
var lastStates = {};

pollTeamcityStatus();

function pollTeamcityStatus() {
    fetch("http://localhost:" + PORT + "/teamcityStatus")
        .then(function (response) {
            return response.json();
        })
        .then(function (data) {
            if (JSON.stringify(data) !== JSON.stringify(lastStates)) {
                lastStates = data;
            } else {
                return;
            }

            clearLayout();

            data.projectStates.forEach(function (projectState) {
                var buildItem = constructBuildItem(projectState);

                mainContainer.appendChild(buildItem);
            })
        })
        .catch(function (reason) {
            clearLayout();

            var container = constructErrorLayout(reason);

            mainContainer.appendChild(container);
        })
        .finally(function () {
            setTimeout(pollTeamcityStatus, 5000);
        });
}

function clearLayout() {
    while (mainContainer.firstChild) {
        mainContainer.removeChild(mainContainer.firstChild);
    }
}

function constructBuildItem(projectState) {
    var container = document.createElement("div");
    var centerContainer = document.createElement("div");
    var nameContainer = document.createElement("div");

    centerContainer.style.margin = "auto";
    centerContainer.classList.add("flex-center");

    nameContainer.style.fontSize = "150%";
    nameContainer.style.fontWeight = "bold";
    nameContainer.textContent += projectState.name;

    container.classList.add("flex-center", "build-item", "card");
    centerContainer.appendChild(nameContainer);
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

        buildNumberContainer.style.position = "absolute";
        buildNumberContainer.style.left = "0";
        buildNumberContainer.style.bottom = "0";
        buildNumberContainer.style.margin = "4px";
        buildNumberContainer.textContent += "#" + projectState.buildNumber;

        container.appendChild(buildNumberContainer);
    }

    return container;
}

function constructErrorLayout(reason) {
    var container = document.createElement("div");
    var text = document.createTextNode(reason.message);

    container.appendChild(text);
    return container;
}
