import React from 'react';
import {Col, Container, Row} from 'react-bootstrap';
import {getEndTime, getRunningTime, getStartTime} from "../../util/DateTimeUtil";
import UpdateTimer from "../../components/UpdateTimer";

class StepExecutionMain extends React.Component {

    render() {
        if (this.props.hidden) {
            return '';
        }
        const stepExecutionInfo = this.props.data;
        return (
            <React.Fragment>
                <Container>
                    <Row>
                        <Col>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>ID:</div>
                                    <div className={'dx-field-value'}>{stepExecutionInfo.id}</div>
                                </div>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Name:</div>
                                    <div className={'dx-field-value'}>{stepExecutionInfo.stepName}</div>
                                </div>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Execution ID:</div>
                                    <div
                                        className={'dx-field-value'}>{stepExecutionInfo ? stepExecutionInfo.stepExecutionId : null}</div>
                                </div>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Status:</div>
                                    <div
                                        className={'dx-field-value'}>{stepExecutionInfo.status}</div>
                                </div>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Start:</div>
                                    <div className={'dx-field-value'}>{getStartTime(stepExecutionInfo)}</div>
                                </div>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>End:</div>
                                    <div className={'dx-field-value'}>{getEndTime(stepExecutionInfo)}</div>
                                </div>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Running Time:</div>
                                    <div className={'dx-field-value'}>{getRunningTime(stepExecutionInfo)}</div>
                                </div>
                            </div>
                        </Col>
                    </Row>
                </Container>
                <UpdateTimer/>
            </React.Fragment>
        );
    }
}

export default StepExecutionMain;
