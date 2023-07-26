
import useSWR from 'swr'
import { useState, useEffect } from 'react'
import { Container, Row, Col, Button, Tab, Tabs } from 'react-bootstrap';
import Loading from '../general/Loading';
import ErrorComponent   from '../general/Error';
import RankingBars from './RankingBars';
import { useNavigate } from 'react-router-dom';

const fetcher = async (url) => {
    const response = await fetch(url);
    if (!response.ok) {
      const errorData = await response.json(); 
      throw new Error(errorData.message); 
    }
    return response.json();
  };


const Home = () => {
    const [loading, setLoading] = useState(false)
    const navigate = useNavigate();

    const { data, error, isLoading } = useSWR('/api/games', fetcher)
    useEffect(() => {
        setLoading(isLoading);
    }, [isLoading]);

    const createGame = () => {
        fetch('/api/games', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response => response.json())
            .then(game => {
                setLoading(true)
                navigate('/game/' + game.id);
            })
            .catch((error) => {
                console.log(error)
            });


    };

    if (error) return <ErrorComponent message={"An error occured fetching the games ranking data." + error.message} />
    if (loading) return <Loading />

    return (<div >
        <Container className="d-flex align-items-center justify-content-center vh-100">
            {data && <div className="text-center ">
                <div className='d-none d-sm-block'>
                    <Tabs defaultActiveKey="first" className="mb-3 ">
                        <Tab eventKey="first" title="Top 10 of all time" >
                            <RankingBars userNames={data.month.userNames} winCounts={data.month.winCounts} />
                        </Tab>
                        <Tab eventKey="second" title="Top 10 of the month">
                            <RankingBars userNames={data.allTime.userNames} winCounts={data.allTime.winCounts} />
                        </Tab>
                    </Tabs>
                </div>
                <Button variant="primary" className="mt-3" onClick={createGame}>
                    Start new game
                </Button>
            </div>}
        </Container>


    </div>);
}

export default Home;