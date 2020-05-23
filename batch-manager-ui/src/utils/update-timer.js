import React from 'react';
import {getCurrentDateTime} from "./date-time-util";
import MethodTimer from "./method-timer";

class UpdateTimer extends React.Component {

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <p>Last update: <span>{getCurrentDateTime()}</span><MethodTimer/></p>
            </React.Fragment>
        );
    }
}

export default UpdateTimer;