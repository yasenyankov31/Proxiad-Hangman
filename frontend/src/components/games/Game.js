import { useState, useEffect } from 'react';
import useSWR from 'swr'
import { Container, Row, Col, Button, Modal, Form, Spinner } from 'react-bootstrap';
import { useParams } from 'react-router-dom'
import GameCanvas from './GameCanvas';
import Loading from '../general/Loading';
import ErrorComponent from '../general/Error';
import { useNavigate } from 'react-router-dom';
import TranslateI18n from '../general/TranslateI18n';

const fetcher = async (url) => {
    const response = await fetch(url);
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
    return response.json();
};




const Game = () => {
    const navigate = useNavigate();
    const letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');
    const { id } = useParams();
    const { data, error, isLoading } = useSWR('/api/games/' + id, fetcher);

    const [attempts, setAttempts] = useState(8);
    const [guessedWord, setGuessedWord] = useState("");
    const [lettersUsed, setLettersUsed] = useState("");
    const [username, setUsername] = useState('');
    const [gameInfoMessage, setGameInfoMessage] = useState('');
    const [gameCompleted, setGameCompleted] = useState(false);
    const [originalWord, setOriginalWord] = useState("");

    const [errorGuessing, setErrorGuessing] = useState(false);
    const [errorGuessingMessage, setErrorGuessingMessage] = useState("true");
    const [loadingEndResult, setLoadingEndResult] = useState(false);

    const [keyboardId, setKeyboardId] = useState("");
    const [formZindex, setFormZindex] = useState("-1");





    const gameOver = () => {
        setKeyboardId("keyboard");
        setLettersUsed(letters);
        setFormZindex("1");
    }

    const resetGame = () => {
        fetch('/api/games/' + id + "/reset", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(game => {
                setAttempts(game.attempts);
                setGuessedWord(game.guessedWord);
                setLettersUsed((game.lettersUsed + game.guessedWord).toUpperCase());

            })
            .catch((error) => {
                setErrorGuessing(true);
                setErrorGuessingMessage(error);
            });
    }

    const completeGame = () => {

        if (username.length < 11) {
            setLoadingEndResult(true);
            fetch('/api/games/' + id + "/ending-result?username=" + username, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                }
            })
                .then(response => response.json())
                .then(game => {
                    setGameCompleted(game.completedGame);
                    setGameInfoMessage(game.gameStatus);
                    setLoadingEndResult(false);
                    setOriginalWord(game.originalWord);
                })
                .catch((error) => {
                    setErrorGuessing(true);
                    setErrorGuessingMessage(error);
                });
        }
        else {
            setErrorGuessing(true);
            setErrorGuessingMessage("Username must be at between 2 and 10 symbols!");
        }

    }

    const newGame = () => {
        fetch('/api/games', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(game => {
                setKeyboardId("");
                setFormZindex("-1");
                setAttempts(game.attemptsLeft);
                setGameInfoMessage("");
                setGameCompleted(game.completedGame);
                setGuessedWord(game.guessedWord);
                setLettersUsed(game.lettersUsed.toUpperCase() + game.guessedWord.toUpperCase());
                navigate('/game/' + game.id);
            })
            .catch((error) => {
                setErrorGuessing(true);
                setErrorGuessingMessage(error.message);
            });


    };

    const guessLetter = (event) => {
        let letter = event.target.innerText
        fetch('/api/games/' + id + "/guess-letter?letter=" + letter.toLowerCase(), {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            }
        })
            .then(response => response.json())
            .then(game => {
                setAttempts(game.attemptsLeft);
                setGuessedWord(game.guessedWord);
                setLettersUsed((game.lettersUsed + game.guessedWord).toUpperCase());
                if (game.gameStatus !== "OnGoing") {
                    gameOver();
                }
                if (game.completedGame) {
                    setGameInfoMessage(game.gameStatus);
                }
            })
            .catch((error) => {
                setErrorGuessing(true);
                setErrorGuessingMessage(error.message);
            });
    }

    const viewProfile = () => {
        navigate('/user/' + username + "/1");
    }

    useEffect(() => {
        if (data) {
            setAttempts(data.attemptsLeft);
            setGameCompleted(data.completedGame);
            setGuessedWord(data.guessedWord);
            setLettersUsed(data.lettersUsed.toUpperCase() + data.guessedWord.toUpperCase());
            if (data.gameStatus !== "OnGoing") {
                gameOver();
            }
            if (data.completedGame) {
                setGameInfoMessage(data.gameStatus);
                setOriginalWord(data.originalWord);
            }
        }

    }, [data]);





    if (error) return <ErrorComponent message={"An error occured fetching the games ranking data." + error.message} />
    if (isLoading) return <Loading />





    const handleClose = () => setErrorGuessing(false);


    return (
        <Container className=" d-flex justify-content-around align-items-center "style={{marginTop:'200px'}} >
            <Modal show={errorGuessing} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Alert</Modal.Title>
                </Modal.Header>
                <Modal.Body className='alert alert-danger'>{errorGuessingMessage}</Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
            <Row >
                <Col xs={12} lg={6} className="d-flex flex-column align-items-center ">
                    <div>
                        <div className='parent'>
                            <div style={{ zIndex: formZindex }} className='container text-center border border-dark rounded username-form'>
                                {!loadingEndResult && !gameInfoMessage && !gameCompleted && <div>
                                    <Form.Label><TranslateI18n id={"GameSubmitNameLabel"}/></Form.Label>
                                    <Form.Control id="username" type="text" placeholder="Your username" onChange={e => setUsername(e.target.value)}
                                         />
                                    <Button className='m-3' onClick={() => { completeGame() }}><TranslateI18n id={"GameSubmitBtn"}/></Button>
                                </div>}

                                {loadingEndResult && !gameInfoMessage && <div style={{ paddingTop: '100px' }}>
                                    <Spinner animation="border" role="status">
                                        <span className="visually-hidden"><TranslateI18n id={"GameSumbitLoading"}/></span>
                                    </Spinner>
                                </div>}
                                {(gameInfoMessage || gameCompleted) && <div >
                                    <Form.Label className='p-3 status'> <TranslateI18n id={"GameEndingStat_1"}/> {gameInfoMessage}. <TranslateI18n id={"GameEndingStat_2"}/> {originalWord}!</Form.Label>
                                    <Button onClick={() => { newGame() }} className='m-1' > <TranslateI18n id={"GamePlayAgainBtn"}/></Button>
                                    <Button onClick={() => { viewProfile() }} ><TranslateI18n id={"GameGetPorfileBtn"}/></Button>
                                </div>}

                            </div>
                            <div id={keyboardId} className="keyboard">
                                {letters.map(letter => (
                                    <Button
                                        id={letter}
                                        onClick={(e) => { guessLetter(e) }}
                                        key={letter}
                                        variant="secondary"
                                        disabled={lettersUsed.includes(letter)}>
                                        {letter}
                                    </Button>
                                ))}
                            </div>
                        </div>
                    </div>
                </Col>
                <Col xs={12} lg={6} >
                    <GameCanvas attempts={attempts}
                        wordToGuess={guessedWord}
                        resetGame={resetGame}
                    >

                    </GameCanvas>
                </Col>
            </Row>
        </Container>
    );
}

export default Game;
