import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import bulbasaur from "../bulbasaur.jpg";

function CardComponent(props) {
    const title = props.title;
    //   const description = props.description;
    const buttonLink = props.buttonLink;
    const imgSource = props.imgSource;
    const address = props.address;
    const condition = props.condition;

    return (
        <Card>
            <Card.Img variant="top" src={imgSource || bulbasaur} />
            <Card.Body>
                <Card.Title>{title}</Card.Title>
                <Card.Text>
                    {condition}
                    <br></br>
                    {address}
                </Card.Text>
                <Button variant="primary" href={buttonLink}>
                    More details
                </Button>
            </Card.Body>
        </Card>
    );
}

export default CardComponent;
