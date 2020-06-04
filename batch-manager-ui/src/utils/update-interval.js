import React, {Component} from 'react';
import {Item} from "devextreme-react/toolbar";

class UpdateInterval extends Component {

    constructor(props) {
        super(props);
        this.state = {
            timer: {}
        }
        this.intervals = [
            {interval: 0, text: 'None'},
            {interval: 30000, text: '30 sec'},
            {interval: 60000, text: '1 min'},
            {interval: 300000, text: '5 min'},
            {interval: 600000, text: '10 min'},
            {interval: 1800000, text: '30 min'},
            {interval: 3600000, text: '1 hour'}];
        this.intervalSelectOptions = {
            width: 140,
            items: this.intervals,
            valueExpr: "interval",
            displayExpr: "text",
            placeholder: "Update interval",
            value: this.intervals[0].id,
            onValueChanged: (args) => {
                clearTimeout(this.state.timer);
                if (args.value > 0) {
                    this.state.timer = setInterval(() => this.setState({}), args.value);
                    this.render();
                }
            }
        }
    }

    render() {
        return (
            <React.Fragment>
                <Item
                    location="after"
                    widget="dxSelectBox"
                    locateInMenu="auto"
                    options={this.intervalSelectOptions}/>
            </React.Fragment>
        )
    }
}

export default UpdateInterval;