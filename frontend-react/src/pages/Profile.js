import React, { Component, Fragment } from "react"
import { Box, Tab, Tabs } from "@mui/material"
import { Grid } from "@mui/material"

import StorageHelper from "../services/StorageHelper"
import ProductService from "../services/ProductService"
import CardComponent from "../components/Card"
import PopUp from "../components/Popup"

function TabPanel(props) {
    const { children, value, index, ...other } = props

    return (
        <Box
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}>
            {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
        </Box>
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
            url: "product/",
            data: [],
            value: 0,
            popup: false,
            popupProduct: 0,
            give: {},
            title: "",
            label: "",
            button: 0
        }
        this.triggerPopUp = this.triggerPopUp.bind(this)
        this.closePopUp = this.closePopUp.bind(this)
    }

    async componentDidMount() {
        const res = await ProductService.getProductByOwner(StorageHelper.getUserId())
        const give = await ProductService.getGAProductByOwner(StorageHelper.getUserId())
        this.setState({ data: res.data })
        const object = give.data.reduce((obj, item) => ((obj[item.id] = item.receiverId), obj), {})
        this.setState({ give: object })
    }

    handleChange = (event, newValue) => {
        this.setState({ value: newValue })
    }
    triggerPopUp = (productId, inputType) => {
        if (inputType === "giveaway"){
            this.setState({
                button: 1,
                title: "Give away",
                label: "Username",
                popup: true,
                popupProduct: productId,
            })
        } else {
            this.setState({
                button: 2,
                title: "Are you sure you want to remove product?",
                popup: true,
                popupProduct: productId,
            })
        }
        
    }

    closePopUp = async (reload, give) => {
        this.setState({
            popup: false,
        })
        if (reload && give) {
            const give = await ProductService.getGAProductByOwner(StorageHelper.getUserId())
            const object = give.data.reduce(
                (obj, item) => ((obj[item.id] = item.receiverId), obj),
                {}
            )
            this.setState({ give: object })
        } else if (reload) {
            window.location.reload('false')
        }
    }
    giveProduct = () => {}
    filterProduct(type) {
        switch (type) {
            case "available":
                return this.state.data.filter((value) => !this.state.give[value.id])
            case "giveaway":
                return this.state.data.filter((value) => this.state.give[value.id])
            default:
                return this.state.data
        }
    }
    render() {
        let available = this.filterProduct("available")
        let availableTab
        if (!available) availableTab = "No Products Found"
        else {
            availableTab = (
                <Box>
                    <Grid container rowSpacing={2} columnSpacing={{ xs: 1, sm: 2, md: 2 }}>
                        {available.map((data, i) => (
                            <Grid item xs={3}>
                                <CardComponent
                                    id={data.id}
                                    title={data.productName}
                                    description={data.description}
                                    address={data.address}
                                    condition={data.condition}
                                    imgSource={data.imageUrl}
                                    buttons={4}
                                    triggerPopUp={this.triggerPopUp}
                                    buttonLink={this.state.url + data.id}
                                    editDetailLink = {this.state.url + "edit/" + data.id}
                                    ownerName={data.ownerName}
                                    dateTime={data.dateTime}>
                                </CardComponent>
                            </Grid>
                        ))}
                    </Grid>
                </Box>
            )
        }
        let giveaway = this.filterProduct("giveaway")
        let giveawayTab
        if (giveaway.length === 0) giveawayTab = "No Products Found"
        else {
            giveawayTab = (
                <Box>
                    <Grid container rowSpacing={2} columnSpacing={{ xs: 1, sm: 2, md: 2 }}>
                        {giveaway.map((data, i) => (
                            <Grid item xs={3}>
                                <CardComponent
                                    title={data.productName}
                                    description={data.description}
                                    address={data.address}
                                    condition={data.condition}
                                    imgSource={data.imageUrl}
                                    buttonLink={this.state.url + data.id}
                                    dateTime={data.dateTime}
                                    ownerName={data.ownerName}>
                                </CardComponent>
                            </Grid>
                        ))}
                    </Grid>
                </Box>
            )
        }
        return (
            <Box>
                <Box>
                    {this.state.popup && ( 
                        <PopUp closePopUp={this.closePopUp} productId={this.state.popupProduct} title={this.state.title} label={this.state.label} buttons={this.state.button} />
                    )}
                </Box>
                <Box>
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
                </Box>
            </Box>
        )
    }
}

export default Profile
