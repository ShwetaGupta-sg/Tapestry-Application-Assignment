document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("ageInput").addEventListener("input", function () {
        let age = this.value;
        let errorSpan = document.getElementById("ageError");
        if (age < 18 || age > 65) {
            errorSpan.textContent = "Age must be between 18 and 65.";
        } else {
            errorSpan.textContent = "";
        }
    });
});
