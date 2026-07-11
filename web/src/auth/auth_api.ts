const BASE_URL = "http://localhost:8080/api/auth";

export async function registerUser(data: {
    firstName: string;
    lastName: string;
    role: string;
    contactNumber: string;
    email: string;
    password: string;
}) {

    const response = await fetch(`${BASE_URL}/register`, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(data)

    });

    if (!response.ok) {
        throw new Error(await response.text());
    }

    return await response.json();
}