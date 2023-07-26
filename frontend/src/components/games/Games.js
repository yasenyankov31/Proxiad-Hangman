import { Container, Table, Button } from 'react-bootstrap';
import Loading from '../general/Loading';
import ErrorComponent from '../general/Error';
import useSWR from 'swr'
import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import PaginationComponent from '../general/Pagination';


const fetcher = async (url) => {
  const response = await fetch(url);
  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message);
  }
  return response.json();
};


const Games = () => {
  const navigate = useNavigate();
  const { page } = useParams();
  const { data, error, isLoading } = useSWR('/api/games/not-completed-games?pageNum='+page, fetcher);

  const [games, setGames] = useState([]);
  const [pageNumber, setPageNumber] = useState(page);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);


  const handlePagination = (pageNumber) => {
    fetch("/api/games/not-completed-games?pageNum=" + pageNumber, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    })
      .then(response => response.json())
      .then(games => {
        setTotalElements(games.totalElements);
        setGames(games.content);
        setTotalPages(games.totalPages);
      });
    setPageNumber(pageNumber);
  }

  const viewGame = (gameId) => {
    navigate('/game/' + gameId)
  };

  useEffect(() => {
    if (data) {
      setTotalElements(data.totalElements);
      setGames(data.content);
      setTotalPages(data.totalPages);
      setPageNumber(Number(page));
    }

  }, [data]);


  if (error) return <ErrorComponent message={"An error occured fetching the games ranking data."
    + error.message} />
  if (isLoading) return <Loading />



  return (
    <Container className="d-flex flex-column align-items-center justify-content-center ">
      <div className="d-flex flex-column align-items-center justify-content-center p-5">
        <h1 className="p-5">All on-going games</h1>
        <Table className='table-dark' striped bordered >
          <thead>
            <tr>
              <th>Attempts left</th>
              <th>Creation date</th>
              <th>Word progress</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {games.map((game, index) => {
              const date = new Date(game.date);
              return (
                <tr key={index}>
                  <td>{game.attemptsLeft}</td>
                  <td>{date.toLocaleDateString()}</td>
                  <td>{game.guessedWord}</td>
                  <td>
                    <Button onClick={() => { viewGame(game.id) }}>Continue</Button>
                  </td>
                </tr>);
           })}
        </tbody>
        </Table>
        <PaginationComponent
            totalPages={totalPages}
            pageNumber={pageNumber}
            totalElements={totalElements}
            handlePagination={handlePagination} />
      </div>
    </Container>

  );
};


export default Games;
