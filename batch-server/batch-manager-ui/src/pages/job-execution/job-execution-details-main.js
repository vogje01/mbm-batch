import Form, {SimpleItem} from "devextreme-react/form";
import React from "react";

class JobExecutionDetailsMain extends React.Component {

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
                    colCount={5}>
                    <SimpleItem dataField="jobName" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="jobVersion" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="hostName" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="nodeName" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="status" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="jobPid" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="jobExecutionId" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="jobGroup" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                    <SimpleItem dataField="jobKey" editorType="dxTextBox" editorOptions={{readOnly: true}}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default JobExecutionDetailsMain