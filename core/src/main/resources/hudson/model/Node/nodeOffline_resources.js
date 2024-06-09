window.displayNode = function (data) {
  let rootUrl = document.head.getAttribute("data-rooturl");
  console.log(rootUrl)
  let p = document.getElementById("projectStatus");
  console.log(data)
  p.style.display = "";

  for (var x = 0; data.length > x; x++) {
    var e = data[x];
    var tr = document.createElement("tr");

    var td1 = document.createElement("td");
    td1.setAttribute("data", e.OnlineOffline);
    td1.classList.add("jenkins-table__cell--tight", "jenkins-table__icon");
    let svg = generateSVGIcon(e.OnlineOffline);
    var div1 = document.createElement("div");
    div1.classList.add("jenkins-table__cell__button-wrapper");
    div1.appendChild(svg);
    td1.appendChild(div1);
    tr.appendChild(td1);

    var td2 = document.createElement("td");
    td2.setAttribute("data", e.User);
    td2.classList.add("jenkins-table__cell--tight", "jenkins-table__icon");
    td2.textContent = e.User
    var div2 = document.createElement("div");
    div2.classList.add("jenkins-table__cell__button-wrapper");
    td2.appendChild(div2);
    tr.appendChild(td2);

    var td3 = document.createElement("td");
    td3.setAttribute("data", e.Date);
    td3.classList.add("jenkins-table__cell--tight", "jenkins-table__icon");
    td3.textContent = e.Date
    var div3 = document.createElement("div");
    div3.classList.add("jenkins-table__cell__button-wrapper");
    td3.appendChild(div3);
    tr.appendChild(td3);

    var td4 = document.createElement("td");
    td4.setAttribute("data", e.offlineReason);
    td4.classList.add("jenkins-table__cell--tight", "jenkins-table__icon");
    td4.textContent = e.offlineReason
    var div4 = document.createElement("div");
    div4.classList.add("jenkins-table__cell__button-wrapper");
    td4.appendChild(div4);
    tr.appendChild(td4);

    p.appendChild(tr);
    Behaviour.applySubtree(tr);
  }
  ts_refresh(p);
};

/**
 * Generate SVG Icon
 */
function generateSVGIcon(iconName) {
  const icons = document.querySelector("#jenkins-computer-status-icons");

  return icons.content.querySelector(`#${iconName}`).cloneNode(true);
}
