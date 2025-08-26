import React, { useState, useEffect } from 'react';
import { Form, Container, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import Select from 'react-select';
import { ELEMENTS } from './Data';



const AddPokemon = () => {
    const [pokemon, setPokemon] = useState({
        name: "",
        hitPoints: 0,
        elements: [],
        attackMoves: [],
        defenseMoves: [],
    })
    const [attackMoves, setAttackMoves] = useState([])
    const [defenseMoves, setDefenseMoves] = useState([])

    const navigate = useNavigate();

    const elementsList = ELEMENTS.map(ele => ({label: ele, value: ele}))


    useEffect(() => {
        // curl -X POST http://localhost:8080/pokemon/create \
        // -H "Content-Type: application/json" \
        // -d '{
        //     "name": "Mewtwo",
        //     "hitPoints": 15.0,
        //     "elements": ["Psychic"],
        //     "attackMoves": ["Psychic", "Water Gun"],              
        //     "defenseMoves": ["Protect", "Block"]  
        // }'
        const fetchMoves = async () => {
            try {
                const response = await fetch('http://localhost:8080/moves/list');
                const moves = await response.json();
                console.log("moves", moves);
                if (moves) {
                    const attackList = moves.filter(move => move.type === 'ATTACK').map(move => ({ value: move.name, label: move.name }));
                    const defenseList = moves.filter(move => move.type === 'DEFENSE').map(move => ({ value: move.name, label: move.name }));
                    setAttackMoves(attackList);
                    setDefenseMoves(defenseList);
                }
            } catch (error) {
                console.error('Fetching moves error', error);
            }
        };

        fetchMoves();
    }, []);

    const handleAddPokemon = async (e) => {
        // Add pokemon using the Add pokemon API 
        e.preventDefault();
        try {
            console.log("pokemon", pokemon);
            const response = await fetch('http://localhost:8080/pokemon/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    name:pokemon.name, 
                    hitPoints:pokemon.hitPoints,
                    elements:pokemon.elements,
                    attackMoves:pokemon.attackMoves,
                    defenseMoves:pokemon.defenseMoves
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
        <h2 className='text-left pb-5'>Create new pokemon</h2>
        <Form onSubmit={handleAddPokemon}>
            <Form.Group className="mb-3">
                <Form.Label>Pokemon name</Form.Label>
                <Form.Control
                    type="text"
                    value={pokemon.name}
                    onChange={(e) => setPokemon({ ...pokemon, name: e.target.value })}
                    required
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Max Hit Points</Form.Label>
                <Form.Control
                    type="number"
                    value={pokemon.hitPoints}
                    onChange={(e) => setPokemon({ ...pokemon, hitPoints: e.target.value })}
                    required
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Elements</Form.Label>
                <Select
                    defaultValue={[]}
                    isMulti
                    name="elements"
                    options={elementsList}
                    onChange={(selected)=> setPokemon({...pokemon, elements:selected.map(item=>item.value)})}
                    className="basic-multi-select"
                    classNamePrefix="select"
                    required
                />


            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Attack moves</Form.Label>
                <Select
                    defaultValue={[]}
                    isMulti
                    name="attack"
                    options={attackMoves}
                    className="basic-multi-select"
                    classNamePrefix="select"
                    onChange={(selected)=> setPokemon({...pokemon, attackMoves:selected.map(item=>item.value)})}
                    required
                />
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Defense moves</Form.Label>
                <Select
                    defaultValue={[]}
                    isMulti
                    name="defense"
                    options={defenseMoves}
                    onChange={(selected)=> setPokemon({...pokemon, defenseMoves:selected.map(item=>item.value)})}
                    className="basic-multi-select"
                    classNamePrefix="select"
                    required
                />
            </Form.Group>
            <Button type='submit'>
                Add pokemon
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
export default AddPokemon; 