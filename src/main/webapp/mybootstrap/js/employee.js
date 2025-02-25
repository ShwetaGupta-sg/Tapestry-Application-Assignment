// Function to Show Image Popup
document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll("[data-component-id='showImage']").forEach(link => {
        link.addEventListener("click", function (event) {
            event.preventDefault();

            let imageUrl = event.target.getAttribute("data-image-url");
            let popup = document.getElementById("employeeImagePopup");
            let image = document.getElementById("employeeImage");

            if (imageUrl) {
                image.src = imageUrl;
                popup.style.display = "block";
            }
        });
    });
});

// Function to Close Popup
function closePopup() {
    document.getElementById("employeeImagePopup").style.display = "none";
}
