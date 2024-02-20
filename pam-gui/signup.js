function displaySignupPage() {
    mainDiv.innerHTML = `
 <section style="width: 400px;">
      <div id="register-page">
        <h2>Register User</h2>
        <img id="previewImage" src="images/defaultUser.jpg" alt="Uploaded Image" style="width: 200px">
        <label class="file-upload stdButton">
          <input type="file" id="imageInput" accept="image/*" onchange="handleImageChange()">
          Upload profile picture
        </label>
        <form id="register-form" class="register-form" onsubmit="registerUser(event)">
          <div class="column one">
            <label for="register-username" class="required">Username:</label>
            <input type="text" id="register-username" placeholder="Fixa validering" required>

            <label for="register-firstname" class="required">First Name:</label>
            <input type="text" id="register-firstname" required>

            <label for="register-email" class="required">Email:</label>
            <input type="email" id="register-email" placeholder="Fixa validering" required>
  
            <label for="register-address">Address:</label>
            <input type="text" id="register-address">          

            <label for="register-city">City:</label>
            <input type="text" id="register-city">
            <button type="submit" class="posButton">Register</button>
          </div>
          <div class="column two">
            <label for="register-password" class="required">Password:</label>
            <input type="password" id="register-password" minlength="8" required>

            <label for="register-lastname">Last Name:</label>
            <input type="text" id="register-lastname">
            
            <label for="register-dateOfBirth">Date of Birth:</label>
            <input type="date" id="register-dateOfBirth">
  
            <label for="register-phone-number">Phone Number:</label>
            <input type="tel" id="register-phone-number">

            <label for="register-postalCode">PostalCode:</label>
            <input type="text" id="register-postalCode">
            <div>
                    
        <button onclick="loadLoginPage()" class="negButton">Return</button>
</div>
          </div>

        </form>
      </div>
    </section>
        `;
}

async function registerUser(event) {
    event.preventDefault();
    loadingGif();

    let newUser = {
        "id": "0",
        "username": document.querySelector("#register-username").value,
        "password": document.querySelector("#register-password").value,
        "firstName": document.querySelector("#register-firstname").value,
        "lastName": document.querySelector("#register-lastname").value,
        "email": document.querySelector("#register-email").value,
        "phone": document.querySelector("#register-phone-number").value,
        "dateOfBirth": document.querySelector("#register-dateOfBirth").value,
        "profilePictureData": {
            "name": uploadedTempProfilePicture.name,
            "type": uploadedTempProfilePicture.type,
            "size": uploadedTempProfilePicture.size,
            "lastModified": uploadedTempProfilePicture.lastModified,
            "lastModifiedDate": uploadedTempProfilePicture.lastModifiedDate,
        },
        // "profilePicture": uploadedTempProfilePicture,
        "profilePic": {
            "$binary": {
                "base64": uploadedTempProfilePicture.$binary.base64
            }
        },
        // "profilePic": {"$binary": {"base64": "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"}},

        "address": {
            "street": document.querySelector("#register-address").value,
            "postalCode": document.querySelector("#register-postalCode").value,
            "city": document.querySelector("#register-city").value
        },
        // "roles": [
        //     {
        //         "id": "0",
        //         "role": ""
        //     }
        // ]
        "role:": ""
    };
    console.log(JSON.stringify(newUser));
    let response;
    const url = 'https://pam-api.gregtech.duckdns.org/api/v1/registeruser';
    let signupUser = btoa(`newUser:newUser`)
    console.log(signupUser)
    try {
        response = await fetchDataPost(url, signupUser, newUser);
        // loadingGif()
    } catch (e) {
        loadLoginPage()
        errorBox("Something went wrong! Try again later.")
    }
    console.log(response)
    if (response.verified) {
        loadLoginPage();
        successBox(response.answer)
    } else if (!response.verifiedUsername) {
        messageBox("Username already in use!")
    } else if (!response.verifiedEmail) {
        messageBox("Email already in use!")
    }
}

let uploadedTempProfilePicture = {"$binary": {}};

function handleImageChange() {
    // Get the file input element
    const input = document.querySelector('#imageInput');
    const file = input.files[0];
    // Get the image element
    let previewImage = document.querySelector('#previewImage');

    // Check if any files are selected
    if (file) {
        let reader = new FileReader();
        uploadedTempProfilePicture.name = file.name;
        uploadedTempProfilePicture.type = file.type;
        uploadedTempProfilePicture.size = file.size;
        uploadedTempProfilePicture.lastModified = file.lastModified;
        uploadedTempProfilePicture.lastModifiedDate = file.lastModifiedDate;

        console.log(JSON.stringify(uploadedTempProfilePicture))
        // console.log(reader)
        // Read the selected image file
        reader.onload = function (e) {
            // The e.target.result contains the data URL of the image
            let imageData = e.target.result;
            // Use the 'blob' object as needed (e.g., send it to a server).

            setTempProfilePicture(imageData);
            // let g = fileToBlob(imageData, file.type);
            // console.log(g)
            // Update the source of the image element
            previewImage.src = imageData;
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function fileToBlob(dataURI, fileType) {
    // Convert base64 data URI to a binary string.
    const byteString = atob(dataURI.split(',')[1]);
    // console.log(byteString)

    // Create a Uint8Array from the binary string.
    const arrayBuffer = new ArrayBuffer(byteString.length);
    const uint8Array = new Uint8Array(arrayBuffer);

    for (let i = 0; i < byteString.length; i++) {
        uint8Array[i] = byteString.charCodeAt(i);
        // console.log(uint8Array)
    }

    // Create a Blob from the Uint8Array with dynamic MIME type and return it.
    return new Blob([uint8Array], {type: fileType});
}

function setTempProfilePicture(imageData) {

    let commaIndex = imageData.indexOf(",", 0)
    console.log(commaIndex);
    console.log(imageData.substring(commaIndex + 1, 50))
    console.log(imageData.length)
    if (imageData.length > 50) {
        uploadedTempProfilePicture.$binary.base64 = imageData.substring(commaIndex + 1);
    }
    console.log(uploadedTempProfilePicture)
}