import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 50,
    duration: '2m',
};

export default function () {
    const login = loginUser();

    if (login && login.token) {
        getOrders(login.token);
        // createOrder(login.token);

        sleep(1);
    }

}

// ------------------
// LOGIN
// ------------------
function loginUser() {
    const url = 'http://localhost:8080/auth-service/api/v1/auth/login';
    const payload = JSON.stringify({
        email: "us1@test.com",
        password: "NewUser123!"
    });

    const params = {
        headers: { 'Content-Type': 'application/json' },
    };

    const res = http.post(url, payload, params);
    check(res, {
        'login status is 200 or 201': (r) => r.status === 200 || r.status === 201,
        'login response not empty': (r) => r.body.length > 0,
    });

    try {
        return JSON.parse(res.body);
    } catch (e) {
        // console.error("Login JSON parse error:", e);
        return null;
    }
}

// ------------------
// GET ORDERS
// ------------------
function getOrders(token) {
    const url = 'http://localhost:8080/order-service/api/v1/orders/1';
    const res = http.get(url, {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
            'X-Tenant-Id': '35c4eace-5c84-4ba1-bb5d-01f08ffe8bb7'
        }
    });

    check(res, {
        'getOrders status 200': (r) => r.status === 200,
        'getOrders response not empty': (r) => r.body.length > 0,
    });
}

// ------------------
// CREATE ORDER (opcional)
// ------------------
// function createOrder(token) {
//     const url = "http://localhost:8080/order-service/api/v1/orders/";
//     const payload = JSON.stringify({ title: `note${__ITER}`, content: `content note${__ITER}`, tagIds: [7] });
//     const res = http.post(url, payload, { headers: getHeaders(token) });
//     check(res, {
//         'createOrder status 200 or 201': (r) => r.status === 200 || r.status === 201,
//         'createOrder response not empty': (r) => r.body.length > 0,
//     });
// }

// function getHeaders(token) {
//     return {
//         'Content-Type': 'application/json',
//         'Authorization': `Bearer ${token}`,
//         'X-Tenant-Id': '35c4eace-5c84-4ba1-bb5d-01f08ffe8bb7'
//     };
// }
