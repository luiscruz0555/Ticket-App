import { useState, useCallback } from "react";
import { useStream } from "react-fetch-streams";
import { Card } from "react-bootstrap";
import classes from "./Home.module.css";
import { Link } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import userEvent from "@testing-library/user-event";

const Home = () => {
  const [streamData, setStreamData] = useState([{}]);
  const [isLoading, setIsLoading] = useState(true);
  const fill = 'a';

  const mockUser = {
    id: "2234373",
    firstname: "Luis",
    lastname: "Cruz",
    email: "testemail@email.com",
    role: "user"
  }

  const fetchParams = {
    headers: "Access-Control-Allow-Origin : *",
    "Content-Type": "text/html",
  };

  const onNext = useCallback(
    async (res) => {
      const streamData = await res.json();
      setStreamData(streamData);
      setIsLoading(false);
    },
    [setStreamData]
  );
  useStream("http://localhost:8081/fems/user/tickets/"+mockUser.id, { onNext });



  return (
    <Container className={classes.container}>
    <Card className={classes.homeCard}>
      <Row>
        <Col lg={6}>
          <div className={classes.profile}>
          </div>
          <div className={classes.userInfo}>
            <h3>Welcome back, {mockUser.firstname} {mockUser.lastname}</h3>
          </div>
          <div>
            <button className={classes.button}>Edit Info</button>
            </div>
          <div>
            <button className={classes.button}>Pay Bill</button>
            </div>
            <div>
            <Link to="/NewTicket"><button className={classes.button}>Enter Ticket</button></Link>
            </div>
          </Col>
          <Col lg={6}>
      {!isLoading && (
      <div className={classes.userTicket}>
          <span>
            {streamData.map((ticket) => (
              <div key="{ticket.id}">
                 <div className={classes.ticketBorder}>
                <label className={classes.name} htmlFor="ticketNum"><b>Ticket Number</b></label>
                <p className={classes.name}>
                  {ticket.id}
                </p>
                <label className={classes.name} htmlFor="issue"><b>Issue</b></label>
                <p className={classes.name}>
                  {ticket.issue}
                </p>
                <label className={classes.name} htmlFor="status"><b>Status</b></label>
                <p className={classes.name}>
                  {ticket.ticketstatus}
                </p>
              </div>
              </div>
            ))}
          </span>
      </div>)}
      {isLoading && (
        <div>Loading...</div>
      )}
      </Col>
      </Row>
    </Card>
    </Container>
  );
};
export default Home;
