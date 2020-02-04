$(function () {
    document.getElementById('checkBox').onchange = function () {
        let plannedDate = document.getElementById('plannedDate');
        plannedDate.value = new Date().toISOString().substr(0, 10);
        plannedDate.readOnly = this.checked;
    };
})