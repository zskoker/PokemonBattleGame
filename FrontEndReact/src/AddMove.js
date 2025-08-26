import React, { useState } from 'react';
import { Form, Container, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { MOVE_TYPES, ELEMENTS } from './Data';

const AddMove = () => {
    const [move, setMove] = useState({
        name: "",
        powerLevel: 0,
        type: "",
        element: "",
        effect: ""
    })
    const navigate = useNavigate();

    const handleAddMove = async (e) => {
        // Add move using the Add move API 
        e.preventDefault();
        try {
            console.log("move", move);
            const response = await fetch('http://localhost:8080/moves/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    moveName:move.name,
                    powerLevel:move.powerLevel,
                    type:move.type.toUpperCase(),
                    element:move.element.toUpperCase(),
                    effect: move.effect, 
                })
            });
            const result = await response.text();
            console.log('Server response:', result);
            if (response.ok) {
                navigate('/');
            }
        } catch (error) {
            console.error('Not able to call the server :', error);
        }
    }


    return <Container className="p-5">
        <h2 className='text-left pb-5'>Create new moves </h2>
        <Form onSubmit={handleAddMove}>
            <Form.Group className="mb-3">
                <Form.Label>Move name</Form.Label>
                <Form.Control
                    type="text"
                    value={move.name}
                    onChange={(e) => setMove({ ...move, name: e.target.value })}
                    required
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Power Level</Form.Label>
                <Form.Control
                    type="number"
                    value={move.powerLevel}
                    onChange={(e) => setMove({ ...move, powerLevel: e.target.value })}
                    required
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Type</Form.Label>
                <Form.Select
                    value={move.type}
                    onChange={(e) => setMove({ ...move, type: e.target.value })}
                    required
                >
                    <option value="">Select move type</option>
                    {MOVE_TYPES.map((type) => (
                        <option key={type} value={type}>
                            {type}
                        </option>
                    ))}
                </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Element</Form.Label>
                <Form.Select
                    value={move.element}
                    onChange={(e) => setMove({ ...move, element: e.target.value })}
                    required
                >
                    <option value="">Select a type</option>
                    {ELEMENTS.map((type) => (
                        <option key={type} value={type}>
                            {type}
                        </option>
                    ))}
                </Form.Select>
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Effect</Form.Label>
                <Form.Control
                    type="text"
                    value={move.effect}
                    onChange={(e) => setMove({ ...move, effect: e.target.value })}
                    required
                />
            </Form.Group>
            <Button type='submit'>
                Add move
            </Button>
        </Form>
        <Button
            variant="secondary"
            onClick={() => navigate('/')}
            className='mt-3'
        >
            Return to Home
        </Button>
    </Container>
}
export default AddMove