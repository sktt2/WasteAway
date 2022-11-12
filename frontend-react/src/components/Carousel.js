function CarouselComponent(props) {
    const title = props.title
    //   const description = props.description;
    const condition = props.condition
    const imgSource = props.imgSource
    const address = props.address

    return (
        <div style={{ borderStyle: "solid" }}>
            <div
                style={{
                    justifyContent: "center",
                    display: "flex",
                    alignItems: "stretched",
                    alignContent: "center",
                    borderBottom: "solid",
                }}>
                <img
                    src={imgSource || ""}
                    alt={imgSource}
                    style={{
                        minWidth: "80%",
                        maxWidth: "80%",
                        minHeight: 250,
                        maxHeight: 250,
                    }}></img>
            </div>
            <div style={{ paddingLeft: "5%" }}>
                <h1>{title}</h1>
                {/* <div>{description}</div> */}
                <div>{condition}</div>
                <div>{address}</div>
            </div>
        </div>
    )
}

export default CarouselComponent
