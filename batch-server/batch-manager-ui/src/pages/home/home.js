import React from 'react';
import './home.scss';
import appInfo from '../../app-info';
import Box, {Item} from 'devextreme-react/box';
import {Button} from 'devextreme-react';
import {Toolbar} from "devextreme-react/toolbar";
import {getItem} from "../../utils/server-connection";
import UpdateTimer from "../../utils/update-timer";
import {withRouter} from "react-router-dom";

class HomeView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            jobStatus: {},
            stepStatus: {},
            status: undefined,
            routed: false
        }
        this.fieldStyle = {marginTop: '-20px', marginBottom: '-20px'}
        this.valueStyle = {textAlign: 'right', height: '60px'}
        this.refreshJobs = this.refreshJobs.bind(this);
    }

    componentDidMount() {
        this.refreshJobs();
    }

    refreshJobs() {
        getItem(process.env.REACT_APP_MANAGER_URL + 'status/jobstatus')
            .then((data) => {
                this.setState({jobStatus: data})
            });
        getItem(process.env.REACT_APP_MANAGER_URL + 'status/stepstatus')
            .then((data) => {
                this.setState({stepStatus: data})
            });
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Dashboard</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <p>Welcome to <b>{appInfo.title}</b>!</p>
                    </div>
                    <Box
                        direction="row"
                        width="100%"
                        height={500}>
                        <Item ratio={1}>
                            <div className={'dx-card responsive-paddings'} style={{padding: '10px 20px 10px 10px'}}>
                                <Toolbar style={{height: '20px'}}>
                                    <Item
                                        location="after"
                                        widget="dxButton"
                                        options={{
                                            icon: "material-icons-outlined ic-refresh", onClick: () => {
                                                this.setState({})
                                            }, hint: 'Refresh list.'
                                        }}/>
                                </Toolbar>
                                <div className="dx-fieldset">
                                    <div className="dx-fieldset-header"><b>Job execution status</b></div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Total</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.totalJobs}
                                            <Button icon="material-icons-outlined ic-search" onClick={() => this.props.history.replace('/jobexecutions')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Started</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.startedJobs}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/jobexecutions/STARTED')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Starting</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.startingJobs}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/jobexecutions/STARTING')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Stopping</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.stoppingJobs}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/jobexecutions/STOPPING')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Stopped</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.stoppedJobs}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/jobexecutions/STOPPED')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Completed</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.completedJobs}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/jobexecutions/COMPLETED')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Failed</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.failedJobs}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/jobexecutions/FAILED')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Abandoned</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.abandonedJobs}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/jobexecutions/ABANDONED')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Unknown</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.unknownJobs}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/jobexecutions/UNKNOWN')}/>
                                        </div>
                                    </div>
                                    <UpdateTimer/>
                                </div>
                            </div>
                        </Item>
                        <Item ratio={1}>
                            <div className={'dx-card responsive-paddings'} style={{padding: '10px 20px 10px 10px'}}>
                                <Toolbar style={{height: '20px'}}>
                                    <Item
                                        location="after"
                                        widget="dxButton"
                                        options={{
                                            icon: "material-icons-outlined ic-refresh", onClick: () => {
                                                this.setState({})
                                            }, hint: 'Refresh list.'
                                        }}/>
                                </Toolbar>
                                <div className="dx-fieldset">
                                    <div className="dx-fieldset-header"><b>Step execution status</b></div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Total</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.stepStatus.totalSteps}
                                            <Button icon="material-icons-outlined ic-search" onClick={() => this.props.history.replace('/stepexecutions')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Started</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.stepStatus.startedSteps}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/stepexecutions/STARTED')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Starting</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.stepStatus.startingSteps}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/stepexecutions/STARTING')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Stopping</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.stepStatus.stoppingSteps}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/stepexecutions/STOPPING')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Stopped</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.stepStatus.stoppedSteps}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/stepexecutions/STOPPED')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Completed</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.stepStatus.completedSteps}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/stepexecutions/COMPLETED')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Failed</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.stepStatus.failedSteps}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/stepexecutions/FAILED')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Abandoned</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.stepStatus.abandonedSteps}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/stepexecutions/ABANDONED')}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Unknown</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.stepStatus.unknownSteps}
                                            <Button icon="material-icons-outlined ic-search"
                                                    onClick={() => this.props.history.replace('/stepexecutions/UNKNOWN')}/>
                                        </div>
                                    </div>
                                    <UpdateTimer/>
                                </div>
                            </div>
                        </Item>
                    </Box>
                </div>
            </React.Fragment>
        )
    };
}

export default withRouter(HomeView);
