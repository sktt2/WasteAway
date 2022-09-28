import bulbasaur from '../bulbasaur.jpg'

function CarouselComponent(props) {
  const title = props.title;
  const description = props.description;
  const condition = props.condition;
  const imgSource = props.imgSource
  const address = props.address

    return (
        <div>
            <img src={bulbasaur} alt={imgSource}></img>
            <h1>{title}</h1>
            {/* <div>{description}</div> */}
            <div>{condition}</div>
            <div>{address}</div>
        </div>
    );
}

export default CarouselComponent