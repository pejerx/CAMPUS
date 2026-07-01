const API_URL = "http://localhost:5173/api/auth";

export type RegisterData = {
  firstName: string;
  lastName: string;
  role: string;
  email: string;
  password: string;
};

export type LoginData = {
  identifier: string;
  password: string;
};

export async function registerUser(data: RegisterData) {
  const response = await fetch(`${API_URL}/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.json();
}

export async function loginUser(data: LoginData) {
  const response = await fetch(`${API_URL}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  });

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.json();
}