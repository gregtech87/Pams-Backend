async function fetchDataGet(url, credentials) {
    try {
        const response = await fetch(url, {
            method: 'GET',
            // referrerPolicy: 'no-referrer-when-downgrade',
            headers: {
                'Accept': 'application/json',
                'Authorization': `Basic ${credentials}`
            }
        });
        if (!response.ok) {
            new Error(`HTTP error! Status: ${response.status}`);
        } else {
            return response;
        }
    } catch (error) {
        console.error('Error: ', error);
    }
}

async function fetchDataPost(url, credentials, formData) {

    try {
        const response = await fetch(url, {
            method: 'POST',
            // referrerPolicy: 'no-referrer-when-downgrade',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Basic ${credentials}`,
            },
            body: JSON.stringify(formData),
        });
        if (!response.ok) {
            new Error('Network response was not ok');
        }
        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}

async function fetchDataPut(url, credentials, formData) {

    try {
        const response = await fetch(url, {
            method: 'PUT',
            // referrerPolicy: 'no-referrer-when-downgrade',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Basic ${credentials}`
            },
            body: JSON.stringify(formData),
        });
        if (!response.ok) {
            new Error('Network response was not ok');
        }
        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
}

async function fetchDataDelete(url, credentials) {
    let deleteMessageFromBackend;
    try {
        await fetch(url, {
            method: 'DELETE',
            // referrerPolicy: 'no-referrer-when-downgrade',
            headers: {
                'Accept': 'application/json',
                'Authorization': `Basic ${credentials}`
            }
        })
            .then(response => response.text())
            .then(data => {
                deleteMessageFromBackend = data;
            })
    } catch (error) {
        console.error('Error: ', error);
    }
    return deleteMessageFromBackend;
}
