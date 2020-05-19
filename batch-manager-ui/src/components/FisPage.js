import React from "react";
import {refreshSubject} from "./MainComponent";
import {filter} from "rxjs/operators";
import {Redirect} from "react-router-dom";

class FisPage extends React.Component {

    constructor(props) {
        super(props);
        this.unsub = refreshSubject
            .pipe(filter(f => f.topic === 'Refresh'))
            .subscribe(() => super.setState({}));
    }

    componentWillUnmount() {
        this.unsub.unsubscribe()
    }

    render() {
        if (localStorage.getItem('webToken') === undefined) {
            return <Redirect to={'/'}/>;
        }
        super.render();
    }
}

export default FisPage;