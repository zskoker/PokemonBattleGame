import { useState, useEffect } from 'react';
import { Container, Row, Col, Button, ListGroup, ListGroupItem, Popover, Card, OverlayTrigger, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const Tournament = () => {
    const navigate = useNavigate();
    const [pokemon, setPokemon] = useState([]);
    const [selectedPokemon, setSelectedPokemon] = useState([]);
    const [loading, setLoading] = useState(true);
    const [seed, setSeed] = useState(null);
    const [winner, setWinner] = useState(null);
    const [tournamentLog, setTournamentLog] = useState(null);


    // a helper function to calculate the power of two 
    const isPowerOfTwo = (n) => {
        if (n <= 1) return false;
        return Math.ceil(Math.log2(n)) === Math.floor(Math.log2(n));
    };

    //handle Pokemon selection

    const handleSelectPoke = (pokemon) => {
        setSelectedPokemon(p => {
            if (p.find(p => p.pokemonId === pokemon.pokemonId)) {
                return p.filter(p => p.pokemonId !== pokemon.pokemonId);
            }
            return [...p, pokemon];
        });
    };

    // fetch pokemon from the endpoint 

    useEffect(() => {
        const fetchPokemon = async () => {
            try {
                const response = await fetch('http://localhost:8080/pokemon/list');
                const data = await response.json();
                if (data) {
                    const pokemon = data.map((pokemon, index) => ({
                        ...pokemon,
                        pokemonId: index + 1  // Adding id
                    }));
                    setPokemon(pokemon);
                    setLoading(false);
                    console.log("battlePokemon", pokemon);
                }
            } catch (error) {
                console.log("Error Fetching Pokemon", error);
            }
        };

        fetchPokemon();
    }, []);

    // handle start tournament, post to the backend to get result; 
    const handleStartTournament = async () => {
        try {
            const response = await fetch('http://localhost:8080/tournament', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    pokemonNames: selectedPokemon.map(pokemon => pokemon.name),
                    seed: seed
                })
            });
            const result = await response.json();
            if (result) {
                setWinner(result.winner);
                setTournamentLog(result.tournamentLog);
                console.log("tournament", result.winner);
            }
        } catch (error) {
            console.error("Error Getting Tournament Result", error);
        }
    };

    // A card to display Pokemon attributes and popups for more info 

    const PokemonCard = ({ pokemon }) => {
        if (!pokemon) return null;
        const renderPopover = (pokemon) => {
            return <Popover>
                <Popover.Header className="text-capitalize">{pokemon.name}</Popover.Header>
                <Popover.Body>
                    <div>
                        {`${pokemon.name} has ${pokemon.currentHitPoints} hp`}
                        <br /><br />
                        Attack Skills:
                        {pokemon.attackMoves && pokemon.attackMoves.map((move, index) =>
                            <div id={index}>
                                {`Name: ${move.name} - Power Level: ${move.powerLevel}`}<br />
                            </div>
                        )}
                        <br /><br />
                        Defense Skills:
                        {pokemon.defenseMoves && pokemon.defenseMoves.map((move, index) =>
                            <div id={index}>
                                {`Name: ${move.name} - Power Level: ${move.powerLevel}`}<br />
                            </div>
                        )}

                        <br />
                        Battle wins: {pokemon.battleWins}
                        <br />
                        Tournament wins: {pokemon.tournamentWins}
                    </div>
                </Popover.Body>
            </Popover>
        }

        return (
            <Card
                key={pokemon.pokemonId}
                className={`${selectedPokemon.find(selectedPoke => selectedPoke.pokemonId === pokemon.pokemonId) ? 'selected' : ''
                    }`}
                onClick={() => !winner && handleSelectPoke(pokemon)}
            >
                <Card.Img
                    variant="top"
                    src={`/img/${pokemon.name}.svg`}
                    onError={(e) => {
                        e.target.src = 'https://purepng.com/public/uploads/large/purepng.com-pokeballpokeballdevicepokemon-ballpokemon-capture-ball-1701527825795ipeio.png';
                    }}
                    style={{
                        height: 200,
                    }}

                />
                <Card.Body className="p-2">
                    <Card.Title className="text-capitalize text-center">
                        {pokemon.name}
                    </Card.Title>
                    <ListGroup>
                        <ListGroupItem>
                            Level: {pokemon.level}
                        </ListGroupItem>
                        <ListGroupItem>
                            Element: {pokemon.element}
                        </ListGroupItem>
                        <ListGroupItem>
                            <OverlayTrigger trigger="click"
                                placement="auto"
                                overlay={renderPopover(pokemon)}>
                                <Button size="sm" variant="link" className="p-0" onClick={(e) => e.stopPropagation()}>Display info</Button>
                            </OverlayTrigger>
                        </ListGroupItem>
                    </ListGroup>
                </Card.Body>
            </Card>
        );
    };

    if (loading) {
        return <div className='text-center p-5'><h2>Pokemon coming to tournament ...</h2></div>;
    }

    return (
        <Container>
            {!winner ? (
                <div>
                    <div className="text-center p-4">
                        <h2>Get Ready for Tournament </h2>
                        <p>Select pokemon for the tournament, the number selected must be be a power of 2</p>
                        <p>{selectedPokemon.length}/{pokemon.length}</p>
                        <Row className="justify-content-center p-2">
                            <Form style={{ maxWidth: 200 }}>
                                <Form.Group className='justify-content-center' >
                                    <Form.Control
                                        type="number"
                                        placeholder="Set a seed number"
                                        value={seed}
                                        onChange={(e) => setSeed(e.target.value)}
                                    />
                                </Form.Group>
                            </Form>
                        </Row>

                        <Button
                            variant="primary"
                            size="lg"
                            disabled={!isPowerOfTwo(selectedPokemon.length) || !seed}
                            onClick={() => handleStartTournament()}
                        >
                            Start Tournament
                        </Button>
                    </div>

                    <Row lg={4} md={2} xs={1} className="g-4">
                        {pokemon.map((pokemon) => (
                            <Col key={pokemon.pokemonId}>
                                <PokemonCard
                                    pokemon={pokemon}
                                    isSelected={selectedPokemon.some(p => p.pokemonId === pokemon.pokemonId)}
                                    onSelect={() => handleSelectPoke(pokemon)}
                                />
                            </Col>
                        ))}
                    </Row>
                </div>) :
                (<div>
                    <h2 className="text-center m-3">Tournament Results</h2>
                    <h4 className="text-center">Tournament Participants</h4>
                    <Row className="justify-content-center">
                        {selectedPokemon.map((pokemon) => (
                            <Col key={pokemon.pokemonId}>
                                <PokemonCard pokemon={pokemon} />
                            </Col>
                        ))}
                    </Row>

                    <Row className="justify-content-center">
                        <Col xs={12} md={8}>
                            <div>
                                <h4 className="text-center m-3">Tournament Log</h4>
                                <div>
                                    {tournamentLog.map((log, index) => (
                                        <div key={index}>
                                            {log === "" ? (
                                                <hr style={{
                                                    border: '0',
                                                    height: '2px',
                                                    backgroundColor: 'gray',
                                                    margin: '20px 0'
                                                }} />
                                            ) : (
                                                <p>{log}</p>
                                            )}
                                        </div>
                                    ))}
                                </div>
                            </div>

                        </Col>
                    </Row>
                    <Row className="justify-content-center mb-4">
                        <Col xs={12} md={6} lg={4}>
                            <PokemonCard pokemon={winner} />
                        </Col>
                    </Row>
                    <Row>
                        <div className="text-center mb-3">
                            <Button
                                variant="primary"
                                onClick={() => navigate('/')}
                                className="mt-2"
                            >
                                Return to Home
                            </Button>
                        </div>
                    </Row>
                </div>)}
        </Container>
    );
};

export default Tournament;