const API_URL = "/api/arrivals";

const form = document.getElementById("arrival-form");
const nameInput = document.getElementById("name");
const message = document.getElementById("message");
const arrivalsBody = document.getElementById("arrivals");

function showMessage(text, isError) {
    message.textContent = text;
    message.className = isError ? "error" : "ok";
}

async function errorText(response) {
    try {
        const body = await response.json();
        return body.error || `Error ${response.status}`;
    } catch {
        return `Error ${response.status}`;
    }
}

function renderArrivals(arrivals) {
    arrivalsBody.replaceChildren();
    if (arrivals.length === 0) {
        const row = arrivalsBody.insertRow();
        const cell = row.insertCell();
        cell.colSpan = 2;
        cell.textContent = "No arrivals registered yet";
        return;
    }
    for (const arrival of arrivals) {
        const row = arrivalsBody.insertRow();
        row.insertCell().textContent = arrival.name;
        row.insertCell().textContent = new Date(arrival.timestamp).toLocaleString();
    }
}

async function loadArrivals() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            showMessage(await errorText(response), true);
            return;
        }
        renderArrivals(await response.json());
    } catch {
        showMessage("Could not connect to the gateway", true);
    }
}

form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const name = nameInput.value.trim();
    if (!name) {
        showMessage("Name is required", true);
        return;
    }
    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({name})
        });
        if (!response.ok) {
            showMessage(await errorText(response), true);
            return;
        }
        const arrival = await response.json();
        showMessage(`Arrival registered for ${arrival.name}`, false);
        form.reset();
        await loadArrivals();
    } catch {
        showMessage("Could not connect to the gateway", true);
    }
});

loadArrivals();
