import React from 'react';
import Form, {Item} from "devextreme-react/form";
import {Button} from "devextreme-react";
import {getLastExecutionTime, getNextExecutionTime} from "../../util/DateTimeUtil";
import {updateItem} from "../../components/ServerConnection";

const screenByWidth = (width) => width < 720 ? 'sm' : 'md';

class JobScheduleMain extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: this.props.data
        };
        this.saveJobSchedule = this.saveJobSchedule.bind(this);
    }

    saveJobSchedule() {
        updateItem(this.state.currentJobSchedule._links.update.href, this.state.currentJobSchedule)
            .then(() => this.props.closePopup());
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        return (
            <React.Fragment>
                <Form
                    id={'form'}
                    formData={this.state.currentJobSchedule}
                    labelLocation={'top'}
                    minColWidth={200}
                    colCount={2}
                    screenByWidth={screenByWidth}>
                    <Item dataField={'id'} editorOptions={{disabled: true}}/>
                    <Item dataField={'active'} editorType={'dxCheckBox'}/>
                    <Item dataField={'lastExecution'} editorType="dxTextBox"
                          editorOptions={{width: '100%', disabled: true, value: getLastExecutionTime(this.state.currentJobSchedule)}}/>
                    <Item dataField={'nextExecution'} editorType="dxTextBox"
                          editorOptions={{width: '100%', disabled: true, value: getNextExecutionTime(this.state.currentJobSchedule)}}/>
                    <Item dataField={'name'} editorOptions={{disabled: true}}/>
                    <Item dataField={'groupName'} editorOptions={{disabled: true}}/>
                    <Item dataField={'schedule'}/>
                </Form>

                <Button
                    width={90}
                    text={'Apply'}
                    type={'success'}
                    stylingMode={'contained'}
                    onClick={this.saveJobSchedule}
                    style={{float: 'right', margin: '5px 10px 0 0'}}/>
            </React.Fragment>
        );
    }
}

export default JobScheduleMain;
