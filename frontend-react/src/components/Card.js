import { Fragment } from "react"
import Button from "react-bootstrap/Button"
import Card from "react-bootstrap/Card"
import bulbasaur from "../bulbasaur.jpg";

// Import CSS styling
import styles from "../features/ComponentStyle.module.css";


function CardComponent(props) {
    const title = props.title
    const id = props.id
    //   const description = props.description;
    const buttonLink = props.buttonLink
    const imgSource = props.imgSource
    const address = props.address
    const condition = props.condition
    const triggerPopUp = props.triggerPopUp
    const { buttons = 1 } = props
    const editDetailLink = props.editDetailLink

    return (
        <Card style={{ minHeight: 450, maxHeight: 450 }}>
            <Card.Img
                variant="top"
                src={imgSource || bulbasaur}
                style={{ minHeight: 225, maxHeight: 225 }}
            />
            <Card.Body>
                <Card.Title>{title}</Card.Title>
                <Card.Text>
                    {condition}
                    <br></br>
                    {address}
                </Card.Text>
                {buttons === 4 && (
                    <div>
                        <div
                            style={{
                                display: "flex",
                                alignItems: "center",
                                justifyContent: "space-between",
                                padding: "0px 10px"
                            }}>
                            <Button variant="primary" href={buttonLink}>
                                More details
                            </Button>
                            <Button
                                variant="primary"
                                onClick={() => {
                                    triggerPopUp(id, "giveaway")
                                }}>
                                Give away
                            </Button>
                        
                        </div>
                        <div style={{
                                display: "flex",
                                alignItems: "center",
                                justifyContent: "space-between",
                                padding: "5px 10px"
                            }}>
                            <Button href={editDetailLink}>
                                Edit details
                            </Button>
                            <Button onClick={()=>{triggerPopUp(id)}}>
                                Delete
                            </Button>
                        </div>
                    </div>
                )}
                {buttons === 2 && (
                    <div
                        style={{
                            display: "flex",
                            alignItems: "center",
                            justifyContent: "space-between",
                            padding: "0px 10px",
                        }}>
                        <Button variant="primary" href={buttonLink}>
                            More details
                        </Button>
                        <Button
                            variant="primary"
                            onClick={() => {
                                triggerPopUp(id)
                            }}>
                            Give away
                        </Button>
                    </div>
                )}
                {buttons === 1 && (
                    <Button variant="primary" href={buttonLink}>
                        More details
                    </Button>
                )}

            </Card.Body>
        </Card>
    )
}

export default CardComponent