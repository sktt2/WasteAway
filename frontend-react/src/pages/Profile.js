import React, { Component, Fragment } from "react"
import "bootstrap/dist/css/bootstrap.min.css"
import StorageHelper from "../services/StorageHelper"
import Col from "react-bootstrap/Col"
import Row from "react-bootstrap/Row"
import { Box, Tab, Tabs, Typography } from "@mui/material"
// From components
import CardComponent from "../components/Card"
import ProductService from "../services/ProductService"
import PopUp from "../components/Popup"

function TabPanel(props) {
    const { children, value, index, ...other } = props

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}>
            {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
        </div>
    )
}
function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        "aria-controls": `simple-tabpanel-${index}`,
    }
}
class Profile extends Component {
    constructor(props) {
        super(props)
        this.state = {
            url: "http://localhost:3000/product/",
            data: [],
            value: 0,
            popup: false,
            popupProduct: 0,
        }
        this.triggerPopUp = this.triggerPopUp.bind(this)
        this.closePopUp = this.closePopUp.bind(this)
    }

    async componentDidMount() {
        const res = await ProductService.getProductByOwner(StorageHelper.getUserId())
        this.setState({ data: res.data })
    }

    handleChange = (event, newValue) => {
        this.setState({ value: newValue })
    }
    triggerPopUp = (productId) => {
        this.setState({
            popup: true,
            popupProduct: productId,
        })
        console.log(this.state)
    }

    closePopUp = (reload) => {
        this.setState({
            popup: false,
        })
        // todo reload when succesfully submitted
        // if (reload) {
        // authService.getGiveawayProductsByOwner
        // }
    }
    giveProduct = () => {}
    filterProduct(type) {
        switch (type) {
            case "available":
                break
            case "giveaway":
                break
            default:
                return this.state.data
        }
    }
    render() {
        // to change to available
        let available = this.filterProduct("avail")
        let availableTab
        if (!available) availableTab = "No Products Found"
        else {
            availableTab = (
                <div className="row">
                    <Row xs={1} md={4} className="g-4">
                        {available.map((data, i) => (
                            <Col key={data.id}>
                                <CardComponent
                                    id={data.id}
                                    title={data.productName}
                                    description={data.description}
                                    address={data.address}
                                    condition={data.condition}
                                    imgSource={data.imageUrl}
                                    buttons={2}
                                    triggerPopUp={this.triggerPopUp}
                                    buttonLink={this.state.url + data.id}></CardComponent>
                            </Col>
                        ))}
                    </Row>
                </div>
            )
        }
        let giveaway = this.filterProduct("giveaway")
        let giveawayTab
        if (!giveaway) giveawayTab = "No Products Found"
        else {
            giveawayTab = (
                <div className="row">
                    <Row xs={1} md={4} className="g-4">
                        {giveaway.map((data, i) => (
                            <Col key={data.id}>
                                <CardComponent
                                    title={data.productName}
                                    description={data.description}
                                    address={data.address}
                                    condition={data.condition}
                                    imgSource={data.imageUrl}
                                    buttonLink={this.state.url + data.id}></CardComponent>
                            </Col>
                        ))}
                    </Row>
                </div>
            )
        }
        return (
            <div>
                <div>
                    {this.state.popup && (
                        <PopUp closePopUp={this.closePopUp} productId={this.state.popupProduct} />
                    )}
                </div>
                <div>
                    <Fragment>
                        <p>
                            <b>{StorageHelper.getName() + "\n"}</b>
                        </p>
                        <p>{StorageHelper.getUserName() + "\n"}</p>
                    </Fragment>

                    <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
                        <Tabs
                            value={this.state.value}
                            onChange={this.handleChange}
                            aria-label="basic tabs example">
                            <Tab label="Available" {...a11yProps(0)} />
                            <Tab label="Gave Away" {...a11yProps(1)} />
                        </Tabs>
                    </Box>
                    <TabPanel value={this.state.value} index={0}>
                        {availableTab}
                    </TabPanel>
                    <TabPanel value={this.state.value} index={1}>
                        {giveawayTab}
                    </TabPanel>
                </div>
            </div>
        )
    }
}

export default Profile
