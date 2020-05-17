import React from 'react';
import {Col, Container, Row} from 'react-bootstrap';
import {getCreateTime, getEndTime, getLastUpdatedTime, getRunningTime, getStartTime} from "../../util/DateTimeUtil";
import UpdateTimer from "../../components/UpdateTimer";

class JobExecutionMain extends React.Component {

    render() {
        if (this.props.hidden) {
            return null;
        }
        const jobExecutionInfo = this.props.data;
        return (
            <React.Fragment>
                <Container>
                    <Row>
                        <Col>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>ID:</div>
                                    <div className={'dx-field-value'}>{jobExecutionInfo.id}</div>
                                </div>
                            </div>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Name:</div>
                                    <div
                                        className={'dx-field-value'}>{jobExecutionInfo.jobName}</div>
                                </div>
                            </div>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Execution ID:</div>
                                    <div className={'dx-field-value'}>{jobExecutionInfo.jobExecutionId}</div>
                                </div>
                            </div>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Version:</div>
                                    <div className={'dx-field-value'}>{jobExecutionInfo.jobVersion}</div>
                                </div>
                            </div>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>PID:</div>
                                    <div className={'dx-field-value'}>{jobExecutionInfo.pid}</div>
                                </div>
                            </div>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Status:</div>
                                    <div className={'dx-field-value'}>{jobExecutionInfo.status}</div>
                                </div>
                            </div>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Create Time:</div>
                                    <div className={'dx-field-value'}>{getCreateTime(jobExecutionInfo)}</div>
                                </div>
                            </div>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Start Time:</div>
                                    <div className={'dx-field-value'}>{getStartTime(jobExecutionInfo)}</div>
                                </div>
                            </div>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>End Time:</div>
                                    <div className={'dx-field-value'}>{getEndTime(jobExecutionInfo)}</div>
                                </div>
                            </div>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Last Update:</div>
                                    <div className={'dx-field-value'}>{getLastUpdatedTime(jobExecutionInfo)}</div>
                                </div>
                            </div>
                            <div className={'content dx-fieldset'}>
                                <div className={'dx-field'}>
                                    <div className={'dx-field-label'}>Running Time:</div>
                                    <div
                                        className={'dx-field-value'}>{getRunningTime(jobExecutionInfo)}</div>
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

export default JobExecutionMain;
