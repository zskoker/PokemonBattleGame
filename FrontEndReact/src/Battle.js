import { useState, useEffect } from 'react';
import { Container, Row, Col, Card, Button, ListGroup, ListGroupItem, OverlayTrigger, Popover, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const Battle = () => {
    const navigate = useNavigate();
    const [selectedPokemon, setSelectedPokemon] = useState([]);
    const [pokemon, setPokemon] = useState([]);
    const [loading, setLoading] = useState(true);
    const [battleStarted, setBattleStarted] = useState(false);
    const [winner, setWinner] = useState(null);
    const [loser, setLoser] = useState(null);
    const [battleLog, setBattleLog] = useState([]);
    const [seed, setSeed] = useState('')

    useEffect(() => {
        const fetchPokemon = async () => {
            try {
                const response = await fetch('http://localhost:8080/pokemon/list');
                const data = await response.json();
                if(data){
                    const pokemon = data.map((pokemon, index) => ({
                        ...pokemon,
                        pokemonId: index + 1  // Adding id
                    }));
                    setPokemon(pokemon);
                    setLoading(false);
                    console.log("battlePokemon", pokemon); 
                }
            } catch (error) {
                console.error('Fetching error', error);
            }
        };

        fetchPokemon();
    }, []);

    // Only allow selecting two Pokemon 

    const handleSelectPoke = (pokemon) => {
        setSelectedPokemon(p => {
            if (p.find(p => p.pokemonId === pokemon.pokemonId)) {
                return p.filter(p => p.pokemonId !== pokemon.pokemonId);
            }
            else if (p.length < 2) {
                return [...p, pokemon];
            }
            return p;
        });
    };

    const handleStartBattle = async () => {
        setBattleStarted(true);
        try {
            const response = await fetch('http://localhost:8080/battle', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    pokemonAName: selectedPokemon[0].name,
                    pokemonBName: selectedPokemon[1].name,
                    seed: seed
                })
            });
            const result = await response.json();
            setWinner(result.winner);
            setLoser(result.loser);
            setBattleLog(result.battleLog);
            console.log(result.winner); 
        } catch (error) {
            console.error('Battle result error :', error);
        }
    };

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
                        {pokemon.attackMoves.map((move, index) =>
                            <div id={index}>
                                {`Name: ${move.name} - Power Level: ${move.powerLevel}`}<br />
                            </div>
                        )}
                        <br /><br />
                        Defense Skills:
                        {pokemon.defenseMoves.map((move, index) =>
                            <div id={index}>
                                {`Name: ${move.name} - Power Level: ${move.powerLevel}`}<br />
                            </div>
                        )}
                        <br />
                        Battle wins: {pokemon.battleWins}
                        <br/>
                        Tournament wins: {pokemon.tournamentWins}
                    </div>
                </Popover.Body>
            </Popover>
        }

        return (
            <Card
                key={pokemon.pokemonId}
                className={`${selectedPokemon.find(p => p.pokemonId === pokemon.pokemonId) ? 'selected' : ''
                    }`}
                onClick={() => !winner && handleSelectPoke(pokemon)}
            >
                <Card.Img
                    variant="top"
                    src={`/img/${pokemon.name}.svg`}
                    onError={(e) => {
                        e.target.src = 'https://purepng.com/public/uploads/large/purepng.com-pokeballpokeballdevicepokemon-ballpokemon-capture-ball-1701527825795ipeio.png';
                    }}
                    alt={pokemon.name}
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
        return <div className='text-center p-5'><h2>Pokemon coming to battle ...</h2></div>;
    }

    return (
        <Container>
            {!battleStarted ? (
                // Select pokemon for battle
                <Container>
                    <div className="text-center">
                        <h2 className='p1'>Get Ready for Pokemon Battle !</h2>
                        <p className='p1'>Select two Pokemon to begin the battle</p>
                        <p className='p1'>Selected: {selectedPokemon.length}/2</p>
                    </div>

                    <div className="text-center p-4">
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
                            disabled={selectedPokemon.length !== 2 || !seed}
                            onClick={handleStartBattle}
                        >
                            Start Battle
                        </Button>
                    </div>
                    <Row lg={4} md={2} xs={1} className="g-4 m-4">
                        {pokemon.map((pokemon) => (
                            <Col key={pokemon.pokemonId}>
                                <PokemonCard pokemon={pokemon} />
                            </Col>
                        ))}
                    </Row>
                </Container>
            ) : (
                // Battle Screen
                <Container>
                    <h2 className="text-center mb-3">Battle Result</h2>
                    <Row className="justify-content-center mb-3">
                        <Col>
                            <PokemonCard
                                pokemon={winner}
                            />
                        </Col>
                        <Col className="text-center d-flex align-items-center justify-content-center">
                            <h3>VS</h3>
                        </Col>
                        <Col>
                            <PokemonCard
                                pokemon={loser}
                            />
                        </Col>
                    </Row>

                    <Row className="justify-content-center">
                        <Col xs={12} md={8}>
                            <div>
                                <h4 className="text-center mb-2">Battle Log</h4>
                                <div className="battle-log-content">
                                    {battleLog.map((log, index) => (
                                        <div key={index}>
                                            {log}
                                        </div>
                                    ))}
                                </div>
                            </div>
                            <div className="text-center mb-3">
                                <Button
                                    variant="primary"
                                    onClick={() => navigate('/')}
                                    className="mt-2"
                                >
                                    Return to Home
                                </Button>
                            </div>
                        </Col>
                    </Row>
                </Container>
            )}
        </Container>
    );
};



export default Battle;