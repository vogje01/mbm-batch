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
                    colCount={5}>
                    <SimpleItem dataField="timestamp" editorType="dxTextBox"
                                editorOptions={{value: getTimestamp(this.state.currentJobExecutionLog, 'timestamp'), readOnly: true}}/>
                    <SimpleItem dataField="level" editorType="dxTextBox"
                                editorOptions={{readOnly: true}}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default JobExecutionLogDetailsMain