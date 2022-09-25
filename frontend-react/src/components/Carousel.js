import bulbasaur from '../bulbasaur.jpg'


function CarouselComponent(props) {
  const title = props.title;
  const description = props.description;
  const buttonLink = props.buttonLink;
  const imgSource = props.imgSource

    return (
        <div style={{width: 100}}>
            <img src={bulbasaur} alt={imgSource}></img>
            <h1>{title}</h1>
            <h2>{description}</h2>
            <div>{buttonLink}</div>
        </div>
    );
}

export default CarouselComponent