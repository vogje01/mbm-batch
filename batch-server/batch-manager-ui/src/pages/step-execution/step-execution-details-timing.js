import Form, {SimpleItem} from "devextreme-react/form";
import {getFormattedTime, getRunningTime} from "../../utils/date-time-util";
import React from "react";

class StepExecutionDetailsTiming extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentStepExecution: props.currentStepExecution
        };
    }

    render() {
        return (
            <React.Fragment>
                <Form
                    formData={this.state.currentStepExecution}
                    colCount={4}>
                    <SimpleItem dataField="startTime" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: getFormattedTime(this.state.currentStepExecution, 'startTime'), readOnly: true}}/>
                    <SimpleItem dataField="endTime" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: getFormattedTime(this.state.currentStepExecution, 'endTime'), readOnly: true}}/>
                    <SimpleItem dataField="createTime" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: getFormattedTime(this.state.currentStepExecution, 'createTime'), readOnly: true}}/>
                    <SimpleItem dataField="lastUpdated" editorType="dxTextBox"
                                editorOptions={{value: getFormattedTime(this.state.currentStepExecution, 'lastUpdated'), readOnly: true}}/>
                    <SimpleItem dataField="runningTime" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: getRunningTime(this.state.currentStepExecution)}}/>
                    <SimpleItem dataField="startedBy" readOnly={true} editorType="dxTextBox"
                                editorOptions={{value: this.state.currentStepExecution.startedBy}}/>
                    <SimpleItem dataField="exitCode" readOnly={true}/>
                    <SimpleItem dataField="exitMessage" readOnly={true}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default StepExecutionDetailsTiming