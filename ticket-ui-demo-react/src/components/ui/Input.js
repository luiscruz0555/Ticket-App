import React, { useState, useRef, useCallback } from "react";
import classes from "./Input.module.css";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "./Card";

const isEmpty = (value) => value.trim() === "";
const url = "http://localhost:8081/fems/saveTicket";
let data = {};
let isSubmitted = false;
let ticket = {};

const Input = (props) => {
  const [formInputValidity, setFormInputValidity] = useState({
    id: true,
    firstname: true,
    lastname: true,
    address: true,
    issue: true,
    status: true,
  });

  const [error, setError] = useState(null);
  const [inputData, setData] = useState({});

  const firstnameInputRef = useRef();
  const lastnameInputRef = useRef();
  const addressInputRef = useRef();
  const issueInputRef = useRef();

  const confirmHandler = (event) => {
    event.preventDefault();

    const enteredFirstname = firstnameInputRef.current.value;
    const enteredLastname = lastnameInputRef.current.value;
    const enteredAddress = addressInputRef.current.value;
    const enteredIssue = issueInputRef.current.value;

    const enteredFirstnameIsValid = !isEmpty(enteredFirstname);
    const enteredLastNameIsValid = !isEmpty(enteredLastname);
    const enteredAddressIsValid = !isEmpty(enteredAddress);
    const enteredIssueIsValid = !isEmpty(enteredIssue);

    setFormInputValidity({
      firstname: enteredFirstnameIsValid,
      lastname: enteredLastNameIsValid,
      address: enteredAddressIsValid,
      issue: enteredIssueIsValid,
    });

    const formIsValid =
      enteredFirstnameIsValid &&
      enteredLastNameIsValid &&
      enteredAddressIsValid &&
      enteredIssueIsValid;

    if (!formIsValid) {
      return;
    }
    ticket = {
      firstname: enteredFirstname,
      lastname: enteredLastname,
      address: enteredAddress,
      issue: enteredIssue,
      ticketstatus: "PENDING",
      authorId: "2234373",
    };
    addTicketHandler(ticket);
    isSubmitted = true;
  };

  async function addTicketHandler(ticket) {
    try {
      const response = await fetch(url, {
        method: "POST",
        body: JSON.stringify(ticket),
        headers: {
          "Access-Control-Allow-Origin": "localhost:8080",
          "Content-Type": "application/json",
          Accept: "application/json",
        },
      });

      if (!response.ok) {
        throw new Error("Something went wrong");
      }

      data = await response.json();
      setData(data);
    } catch (error) {
      setError(error.message);
    }
  }

  return (
    <Container className="container">
      <Card className={classes.inputCard}>
        <Row>
          <Col lg={6}>
            <form className={classes.inputColumn} onSubmit={confirmHandler}>
              <div className={classes.form}>
                <div
                  className={`${classes.control} ${
                    formInputValidity.firstname ? "" : classes.invalid
                  }`}
                >
                  <label className={classes.label} htmlFor="firstname">
                    First Name
                  </label>
                  <input ref={firstnameInputRef} type="text" id="firstname" />
                  {!formInputValidity.firstname && (
                    <p className={classes.error_text}>
                      Please enter a valid first name.
                    </p>
                  )}
                  <label
                    className={classes.space && classes.label}
                    htmlFor="lastname"
                  >
                    Last Name
                  </label>
                  <input ref={lastnameInputRef} type="text" id="lastname" />
                  {!formInputValidity.lastname && (
                    <p className={classes.error_text}>
                      Please enter a valid last name.
                    </p>
                  )}
                </div>
                <div className={classes.align}>
                  <div
                    className={`${classes.control && classes.align} ${
                      formInputValidity.address ? "" : classes.invalid
                    }`}
                  >
                    <label htmlFor="address">Address</label>
                    <input ref={addressInputRef} type="text" id="address" />
                    {!formInputValidity.address && (
                      <p className={classes.error_text}>
                        Please enter a valid address.
                      </p>
                    )}
                  </div>

                  <div
                    className={`${classes.control && classes.align} ${
                      formInputValidity.issue ? "" : classes.invalid
                    }`}
                  >
                    <label htmlFor="issue">Issue</label>
                    <input ref={issueInputRef} type="text" id="issue" />
                    {!formInputValidity.issue && (
                      <p className={classes.error_text}>
                        "Issue" cannot be left blank!
                      </p>
                    )}
                  </div>
                </div>

                <button className={classes.button}>Submit</button>
              </div>
            </form>
          </Col>

          {isSubmitted && (
            <Col lg={6}>
              <Card className={classes.submittedticket}>
                <h1 className={classes.confirmation}>
                  Confirmation #{data.id}
                </h1>
                <div className={classes.ticketBorder}>
                  <label className={classes.name} htmlFor="name">
                    <b>Name</b>
                  </label>
                  <p className={classes.name}>
                    {data.firstname} {data.lastname}
                  </p>
                </div>
                <div className={classes.ticketBorder}>
                  <label className={classes.name} htmlFor="address">
                    <b>Address</b>
                  </label>
                  <p className={classes.name}>{data.address}</p>
                </div>
                <div className={classes.ticketBorder}>
                  <label className={classes.name} htmlFor="name">
                    <b>Issue</b>
                  </label>
                  <p className={classes.name}>{data.issue}</p>
                </div>
                <div className={classes.ticketBorder}>
                  <label className={classes.name} htmlFor="status">
                    <b>Status</b>
                  </label>
                  <p className={classes.name}>{data.ticketstatus}</p>
                </div>
              </Card>
            </Col>
          )}
        </Row>
      </Card>
    </Container>
  );
};

export default Input;
