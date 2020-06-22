import React from 'react';
import './home.scss';
import appInfo from '../../app-info';
import {getItem} from "../../utils/server-connection";
import {Toolbar} from "devextreme-react/toolbar";
import UpdateTimer from "../../utils/update-timer";
import Box, {Item} from 'devextreme-react/box';

class HomeView extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            jobStatus: {}
        }
        this.fieldStyle = {marginTop: '-20px', marginBottom: '-20px'}
        this.valueStyle = {textAlign: 'right'}
        this.refresh = this.refresh.bind(this);
    }

    componentDidMount() {
        this.refresh();
    }

    refresh() {
        getItem(process.env.REACT_APP_API_URL + 'status/jobstatus')
            .then((data) => {
                this.setState({jobStatus: data})
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
