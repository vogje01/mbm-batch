import Form, {SimpleItem, StringLengthRule} from "devextreme-react/form";
import React from "react";
import {CustomRule, RequiredRule} from "devextreme-react/validator";
import {JobDefinitionDataSource} from "../job-definition/job-definition-data-source";
import {getFormattedTime} from "../../utils/date-time-util";

class JobScheduleDetailsMain extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            currentJobSchedule: props.currentJobSchedule
        };
        this.jobScheduleModes = [
            'FIXED', 'RANDOM', 'RANDOM_GROUP', 'MINIMUM_LOAD'
        ];
        this.jobScheduleTypes = [
            'CENTRAL', 'LOCAL'
        ]
    }

    render() {
        return (
            <React.Fragment>
                <Form
                    formData={this.state.currentJobSchedule}
                    colCount={5}>
                    <SimpleItem dataField="name">
                        <RequiredRule message="Schedule name required"/>
                        <StringLengthRule min={2} message="Schedule name must be at least 2 characters long."/>
                    </SimpleItem>
                    <SimpleItem dataField="jobDefinitionName"
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: JobDefinitionDataSource(), valueExpr: 'name', displayExpr: 'name'}}>
                        <RequiredRule message="Job name required"/>
                        <StringLengthRule min={2} message="Job name must be at least 2 characters long."/>
                    </SimpleItem>
                    <SimpleItem dataField={'lastSchedule'} editorType={'dxTextBox'}
                                editorOptions={{readOnly: true, value: getFormattedTime(this.state.currentJobSchedule, 'lastSchedule')}}/>
                    <SimpleItem dataField={'nextSchedule'} editorType={'dxTextBox'}
                                editorOptions={{readOnly: true, value: getFormattedTime(this.state.currentJobSchedule, 'nextSchedule')}}/>
                    <SimpleItem dataField="schedule"/>
                    <SimpleItem dataField="type"
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: this.jobScheduleTypes}}>
                        <RequiredRule message="Job schedule type is required"/>
                        <CustomRule
                            validationCallback={this.validateType}
                            message="With a local scheduler type only fixed mode is allowed"/>
                    </SimpleItem>
                    <SimpleItem dataField="mode"
                                editorType={'dxSelectBox'}
                                editorOptions={{dataSource: this.jobScheduleModes}}>
                        <RequiredRule message="Job schedule mode is required"/>
                    </SimpleItem>
                    <SimpleItem dataField="active" editorType={"dxCheckBox"} colSpan={2}/>
                </Form>
            </React.Fragment>
        );
    }
}

export default JobScheduleDetailsMain