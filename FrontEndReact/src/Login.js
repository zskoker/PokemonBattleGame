import React, { useState } from 'react';
import { Form, Button, Container, Alert } from "react-bootstrap";


const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [loginError, setLoginError] = useState("")

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            console.log(username);
            console.log(password);
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                })
            });
            const result = await response.json();

            if(!result.userId){
                setLoginError("Either password or username is incorrect ")
            }
            else {
                const loginUser = {
                    userId: result.userId,
                    username: result.username,
                    role: result.role,
                }
                localStorage.setItem('user', JSON.stringify(loginUser))
                //force reloading 
                window.location.href = '/';
            }

        } catch (error) {
            console.error('Not able to call the server :', error);
        }
    }

    return (
        <div className='justify-content-center'>
            <Container className='p-4'>
                <h2>Welcome to the Pokemon App</h2>
                {loginError && <Alert key="warning" variant="warning">
                    {loginError}
                </Alert>}
                <Form onSubmit={handleLogin}>
                    <Form.Group className="mb-3">
                        <Form.Label>Username</Form.Label>
                        <Form.Control
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group className="mb-3">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Button type='submit'>
                        Login
                    </Button>
                </Form>
            </Container>

        </div>
    )
}

export default Login