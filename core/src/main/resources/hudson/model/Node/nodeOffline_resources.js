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
    td1.setAttribute("data", e.offlineReason);
    td1.classList.add("jenkins-table__cell--tight", "jenkins-table__icon");
    td1.textContent = e.offlineReason
    var div1 = document.createElement("div");
    div1.classList.add("jenkins-table__cell__button-wrapper");
    td1.appendChild(div1);
    tr.appendChild(td1);

    var td2 = document.createElement("td");
    td2.setAttribute("data", e.offlineReason);
    td2.classList.add("jenkins-table__cell--tight", "jenkins-table__icon");
    td2.textContent = e.offlineReason
    var div2 = document.createElement("div");
    div2.classList.add("jenkins-table__cell__button-wrapper");
    td2.appendChild(div2);
    tr.appendChild(td2);

    var td3 = document.createElement("td");
    td3.setAttribute("data", e.offlineReason);
    td3.classList.add("jenkins-table__cell--tight", "jenkins-table__icon");
    td3.textContent = e.offlineReason
    var div3 = document.createElement("div");
    div3.classList.add("jenkins-table__cell__button-wrapper");
    td3.appendChild(div3);
    tr.appendChild(td3);

    p.appendChild(tr);
    Behaviour.applySubtree(tr);
  }
  ts_refresh(p);
};
