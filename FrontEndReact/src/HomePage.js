import { useState, useEffect } from "react";
import { Row } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import Container from 'react-bootstrap/Container';
import { useNavigate } from 'react-router-dom';


function HomePage() {
    const [userRole, setUserRole] = useState("")
    const navigate = useNavigate();
    const handleLogOut = () => {
        localStorage.removeItem('user');
        navigate('/login');
    }

    useEffect(() => {
        const user = JSON.parse(localStorage.getItem("user"));
        console.log(user);
        setUserRole(user.role);
    }, [userRole])

    return (
        <div>
            <header className="p-5 bg-success mb-5">
                <Container>
                    <h1 className="text-center text-light">
                        Welcome to the thunder dome!
                    </h1>
                    <Row className="justify-content-center"><Button variant="link" className="text-light" onClick={handleLogOut}>Logout </Button></Row>
                    {userRole === "admin" && <div><Row className="justify-content-center"><Button variant="link" className="text-light" onClick={() => navigate("/add_move")}>Add move</Button></Row>
                        <Row className="justify-content-center"><Button variant="link" className="text-light" onClick={() => navigate("/add_pokemon")}>Add pokemon</Button></Row>
                    </div>}
                </Container>
            </header>
            <Container>
                <Row className="justify-content-center">
                    <Button variant="light" size="lg" className="col-auto me-3" onClick={() => navigate('/tournament')}>Start tournament</Button>
                    <Button variant="light" size="lg" className="col-auto" onClick={() => navigate('/battle')}>Start battle</Button>
                </Row>
            </Container>
        </div>
    );
}

export default HomePage;