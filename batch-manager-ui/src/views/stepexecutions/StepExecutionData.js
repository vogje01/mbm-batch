import React from 'react';
import {Col, Container, Row} from 'react-bootstrap';

class StepExecutionData extends React.Component {

    getPercentage(total, value) {
        if (total > 0) {
            return ' (' + Number.parseFloat(value / total * 100).toFixed(2) + '%)';
        }
        return null;
    }

    render() {
        if (this.props.hidden) {
            return null;
        }
        const stepExecutionInfo = this.props.data;

        const totalValue = stepExecutionInfo ? stepExecutionInfo.totalCount : 0;
        const readValue = stepExecutionInfo ? stepExecutionInfo.readCount : 0;
        const readSkipValue = stepExecutionInfo ? stepExecutionInfo.readSkipCount : 0;
        const writeValue = stepExecutionInfo ? stepExecutionInfo.writeCount : 0;
        const writeSkipValue = stepExecutionInfo ? stepExecutionInfo.writeSkipCount : 0;
        const commitValue = stepExecutionInfo ? stepExecutionInfo.commitCount : 0;
        const rollbackValue = stepExecutionInfo ? stepExecutionInfo.rollbackCount : 0;
        const processSkipValue = stepExecutionInfo ? stepExecutionInfo.processSkipCount : 0;
        const readPct = this.getPercentage(totalValue, readValue);
        const readSkipPct = this.getPercentage(totalValue, readSkipValue);
        const writePct = this.getPercentage(totalValue, writeValue);
        const writeSkipPct = this.getPercentage(totalValue, writeSkipValue);
        const rollbackPct = this.getPercentage(totalValue, rollbackValue);
        const processSkipPct = this.getPercentage(totalValue, processSkipValue);
        const chunkSize = commitValue > 0 ? readValue / commitValue : 0;
        return (
            <Container>
                <Row>
                    <Col>
                        <div className={'content dx-fieldset'}>
                            <div className={'dx-field'}>
                                <div className={'dx-field-label'}>Chunk Size:</div>
                                <div className={'dx-field-value'}>{chunkSize}</div>
                            </div>
                            <div className={'dx-field'}>
                                <div className={'dx-field-label'}>Commits:</div>
                                <div className={'dx-field-value'}>{stepExecutionInfo ? stepExecutionInfo.commitCount : 0}</div>
                            </div>
                            <div className={'dx-field'}>
                                <div className={'dx-field-label'}>Total:</div>
                                <div className={'dx-field-value'}>{totalValue}</div>
                            </div>
                            <div className={'dx-field'}>
                                <div className={'dx-field-label'}>Read:</div>
                                <div className={'dx-field-value'}>{readValue + readPct}</div>
                            </div>
                            <div className={'dx-field'}>
                                <div className={'dx-field-label'}>Skipped Reads:</div>
                                <div className={'dx-field-value'}>{readSkipValue + readSkipPct}</div>
                            </div>
                            <div className={'dx-field'}>
                                <div className={'dx-field-label'}>Write:</div>
                                <div className={'dx-field-value'}>{writeValue + writePct}</div>
                            </div>
                            <div className={'dx-field'}>
                                <div className={'dx-field-label'}>Skipped Writes:</div>
                                <div className={'dx-field-value'}>{writeSkipValue + writeSkipPct}</div>
                            </div>
                            <div className={'dx-field'}>
                                <div className={'dx-field-label'}>Rollback:</div>
                                <div className={'dx-field-value'}>{rollbackValue + rollbackPct}</div>
                            </div>
                            <div className={'dx-field'}>
                                <div className={'dx-field-label'}>Skipped process:</div>
                                <div className={'dx-field-value'}>{processSkipValue + processSkipPct}</div>
                            </div>
                        </div>
                    </Col>
                </Row>
            </Container>
        );
    }
}

export default StepExecutionData;
