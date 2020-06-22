import React from 'react';
import './home.scss';
import appInfo from '../../app-info';
import Box, {Item} from 'devextreme-react/box';
import {Button} from 'devextreme-react';
import {Toolbar} from "devextreme-react/toolbar";
import {getItem} from "../../utils/server-connection";
import UpdateTimer from "../../utils/update-timer";

class HomeView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            jobStatus: {},
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
        getItem(process.env.REACT_APP_API_URL + 'status/jobstatus')
            .then((data) => {
                this.setState({jobStatus: data})
            });
    }

    jobTotalClicked() {
        this.props.history.push('/jobexecutions')
    }

    jobStartingClicked() {
        this.props.history.push('/jobexecutions/STARTING')
    }

    jobStartedClicked() {
        this.props.history.push('/jobexecutions/STARTED')
    }

    jobStoppingClicked() {
        this.props.history.push('/jobexecutions/STOPPING')
    }

    jobStoppedClicked() {
        this.props.history.push('/jobexecutions/STOPPED')
    }

    jobCompletedClicked() {
        this.props.history.push('/jobexecutions/COMPLETED')
    }

    jobFailedClicked() {
        this.props.history.push('/jobexecutions/FAILED')
    }

    jobAbandonedClicked() {
        this.props.history.push('/jobexecutions/ABANDONED')
    }

    jobUnknownClicked() {
        this.props.history.push('/jobexecutions/UNKNOWN')
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
                                            <Button icon="material-icons-outlined ic-search" onClick={this.jobTotalClicked.bind(this)}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Started</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.startedJobs}
                                            <Button icon="material-icons-outlined ic-search" onClick={this.jobStartedClicked.bind(this)}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Starting</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.startingJobs}
                                            <Button icon="material-icons-outlined ic-search" onClick={this.jobStartingClicked.bind(this)}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Stopping</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.stoppingJobs}
                                            <Button icon="material-icons-outlined ic-search" onClick={this.jobStoppingClicked.bind(this)}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Stopped</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.stoppedJobs}
                                            <Button icon="material-icons-outlined ic-search" onClick={this.jobStoppedClicked.bind(this)}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Completed</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.completedJobs}
                                            <Button icon="material-icons-outlined ic-search" onClick={this.jobCompletedClicked.bind(this)}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Failed</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.failedJobs}
                                            <Button icon="material-icons-outlined ic-search" onClick={this.jobFailedClicked.bind(this)}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Abandoned</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.abandonedJobs}
                                            <Button icon="material-icons-outlined ic-search" onClick={this.jobAbandonedClicked.bind(this)}/>
                                        </div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Unknown</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.unknownJobs}
                                            <Button icon="material-icons-outlined ic-search" onClick={this.jobUnknownClicked.bind(this)}/>
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
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.totalJobs}</div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Started</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.startedJobs}</div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Starting</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.startingJobs}</div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Stopping</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.stoppingJobs}</div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Stopped</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.stoppedJobs}</div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Completed</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.completedJobs}</div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Failed</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.failedJobs}</div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Abandoned</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.abandonedJobs}</div>
                                    </div>
                                    <div className="dx-field" style={this.fieldStyle}>
                                        <div className="dx-field-label">Unknown</div>
                                        <div className="dx-field-value-static" style={this.valueStyle}>{this.state.jobStatus.unknownJobs}</div>
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

export default HomeView;
