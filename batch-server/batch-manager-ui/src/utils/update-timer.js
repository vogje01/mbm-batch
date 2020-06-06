import React from 'react';
import {getCurrentDateTime} from "./date-time-util";
import MethodTimer from "./method-timer";

export const updateIntervals = [
    {interval: 0, text: 'None'},
    {interval: 30000, text: '30 sec'},
    {interval: 60000, text: '1 min'},
    {interval: 300000, text: '5 min'},
    {interval: 600000, text: '10 min'},
    {interval: 1800000, text: '30 min'},
    {interval: 3600000, text: '1 hour'}];

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