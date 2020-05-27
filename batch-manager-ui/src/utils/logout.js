import * as React from "react";
import {Redirect} from "react-router-dom";

class Logout extends React.Component {

    constructor(props) {
        super(props);
        this.state = {navigate: false};
    }

    logout = () => {
        localStorage.clear();
        this.setState({navigate: true});
    }

    render() {
        const {navigate} = this.state;

        if (navigate) {
            return <Redirect to={'/'} push={true}/>
        }
    }
}

export default Logout;