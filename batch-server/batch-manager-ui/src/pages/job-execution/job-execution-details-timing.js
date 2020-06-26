import Form, {SimpleItem} from "devextreme-react/form";
import {getFormattedTime, getRunningTime} from "../../utils/date-time-util";
import React from "react";

class JobExecutionDetailsTiming extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecution: props.currentJobExecution
        };
    }

    render() {
        return (
            <React.Fragment>
                <Form
                    formData={this.state.currentJobExecution}
                    colCount={4}>
                    <SimpleItem dataField="startTime" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: getFormattedTime(this.state.currentJobExecution, 'startTime'), readOnly: true}}/>
                    <SimpleItem dataField="endTime" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: getFormattedTime(this.state.currentJobExecution, 'endTime'), readOnly: true}}/>
                    <SimpleItem dataField="createTime" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: getFormattedTime(this.state.currentJobExecution, 'createTime'), readOnly: true}}/>
                    <SimpleItem dataField="lastUpdated" editorType="dxTextBox"
                                editorOptions={{value: getFormattedTime(this.state.currentJobExecution, 'lastUpdated'), readOnly: true}}/>
                    <SimpleItem dataField="runningTime" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: getRunningTime(this.state.currentJobExecution)}}/>
                    <SimpleItem dataField="startedBy" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: this.state.currentJobExecution.startedBy}}/>
                    <SimpleItem dataField="exitCode" readOnly={true}/>
                    <SimpleItem dataField="exitMessage" readOnly={true}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default JobExecutionDetailsTiming