import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import axios from "axios";

import InfiniteScroll from "react-infinite-scroller";
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';

import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";

// From components
import CardComponent from '../components/Card';
import CarouselComponent from '../components/Carousel';




const responsive = {
        desktop: {
            breakpoint: { max: 3000, min: 1024 },
            items: 3,
            slidesToSlide: 2 // optional, default to 1.
        }
    };

class Product extends Component {

    // Making use of the pokemon api first
    state = {
        url: "https://pokeapi.co/api/v2/pokemon/?limit=200",
        pokemon: [],
        itemsCountPerPage: 20,
        activePage: 1
    };

    
    async componentDidMount() {
        const res = await axios.get(this.state.url);
        this.setState({ pokemon: res.data["results"] });
    }

    loadPokemon = () => {
        axios
        .get(this.state.url)
        .then(res => {
            this.setState(prevState => {
            return {
                pokemon: [...prevState.pokemon, ...res.data.results],
                url: res.data.next
            };
            });
        })
        .catch(function(error) {
            // handle error
            console.log(error);
        });
    };

    render() {
        return (
            <div className="container">
                <Carousel responsive={responsive}>
                    {this.state.pokemon.map((pokemon, i)=>(
                        <CarouselComponent title={pokemon.name} description='hello' imgSource="address" buttonLink={pokemon.url} ></CarouselComponent>
                    ))}
                </Carousel>

                <br></br>

                <React.Fragment>
                    {this.state.pokemon ? (
                        <div className="row">
                            <InfiniteScroll>
                                <Row xs={1} md={4} className="g-4">
                                    {this.state.pokemon.map((pokemon, i) => (
                                        <Col>
                                        <CardComponent title={pokemon.name} description='hello' imgSource="address" buttonLink={pokemon.url}></CardComponent>
                                        </Col>
                                    ))}
                                </Row>
                            </InfiniteScroll>
                        </div>
                    ):(<h1>Loading Pokemon</h1>)}
                </React.Fragment>
            </div>
        );
    }
}


export default Product
