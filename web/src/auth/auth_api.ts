import API_BASE_URL from "../api/api_config";

const AUTH_API_URL = `${API_BASE_URL}/api/auth`;

export async function registerUser(data: {
    firstName: string;
    lastName: string;
    role: string;
    contactNumber: string;
    email: string;
    password: string;
}) {

    const response = await fetch(`${AUTH_API_URL}/register`, {

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