pollTeamcityStatus();

function pollTeamcityStatus() {
    fetch("http://localhost:8080/teamcityStatus")
        .then(function (response) {
            return response.json();
        })
        .then(function (parsedResponse) {
            while (document.body.firstChild) {
                document.body.removeChild(document.body.firstChild);
            }

            parsedResponse.projectStates.forEach(function (projectState) {
                var container = document.createElement("div");
                var text = document.createTextNode(projectState.name);

                container.classList.add("build-item");

                if (projectState.status === 'SUCCESS') {
                    container.classList.add("build-item-success");
                } else if (projectState.status === 'FAILURE') {
                    container.classList.add("build-item-failure");
                } else {
                    container.classList.add("build-item-unknown");
                }

                container.appendChild(text);
                document.body.appendChild(container);
            })
        })
        .catch(function (reason) {
            while (document.body.firstChild) {
                document.body.removeChild(document.body.firstChild);
            }

            var container = document.createElement("div");
            var text = document.createTextNode(reason.message);

            container.appendChild(text);
            document.body.appendChild(container);
        })
        .finally(function () {
            setTimeout(pollTeamcityStatus, INTERVAL);
        });
}
