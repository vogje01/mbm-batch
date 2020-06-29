import Form, {SimpleItem} from "devextreme-react/form";
import React from "react";
import {getTimestamp} from "../../utils/date-time-util";

class JobExecutionLogDetailsMain extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobExecutionLog: props.currentJobExecutionLog
        };
    }

    render() {
        return (
            <React.Fragment>
                <Form
                    formData={this.state.currentJobExecutionLog}
                    colCount={4}>
                    <SimpleItem dataField="timestamp" editorType="dxTextBox"
                                editorOptions={{value: getTimestamp(this.state.currentJobExecutionLog, 'timestamp'), readOnly: true}}/>
                    <SimpleItem dataField="level" editorType="dxTextBox"
                                editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="loggerName" editorType="dxTextBox" colSpan={2}
                                editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="jobName" editorType="dxTextBox"
                                editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="stepName" editorType="dxTextBox"
                                editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="jobVersion" editorType="dxTextBox"
                                editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="jobPid" editorType="dxTextBox"
                                editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="message" editorType="dxTextArea" colSpan={4}
                                editorOptions={{readOnly: true}}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default JobExecutionLogDetailsMain