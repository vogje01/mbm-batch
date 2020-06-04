import React from "react";
import UpdateTimer from "../../utils/update-timer";
import './performance.scss'
import moment from "moment";
import {getItem} from "../../utils/server-connection";
import {
    ArgumentAxis,
    Chart,
    CommonSeriesSettings,
    Crosshair,
    Export,
    Grid,
    HorizontalLine,
    Label,
    Legend,
    Point,
    Series,
    Tick,
    TickInterval,
    Title,
    ValueAxis,
    VerticalLine
} from "devextreme-react/chart";
import SelectBox from 'devextreme-react/select-box';
import {PerformanceDataSource} from "./performance-data-source";
import {DateBox} from 'devextreme-react';

const sources = [
    {value: 'host.total.system.load', valueField: 'value', name: 'System Load', yAxisTitle: 'System Load [%]', scaleHidden: true},
    {value: 'host.real.memory.free.pct', name: 'Free Real Memory Percentage', yAxisTitle: 'Free Real Memory [%]', scaleHidden: true},
    {value: 'host.real.memory.used.pct', name: 'Used Real Memory Percentage', yAxisTitle: 'Used Real Memory [%]', scaleHidden: true},
    {value: 'freeVirtMemoryPct', name: 'Free Virtual Memory Percentage', yAxisTitle: 'Free Virtual Memory [%]', scaleHidden: true},
    {value: 'usedVirtMemoryPct', name: 'Used Virtual Memory Percentage', yAxisTitle: 'Used Virtual Memory [%]', scaleHidden: true},
    {value: 'freeSwapPct', name: 'Free Swap Percentage', yAxisTitle: 'Free Swap [%]', scaleHidden: true},
    {value: 'usedSwapPct', name: 'Used Swap Percentage', yAxisTitle: 'Used Swap [%]', scaleHidden: true},
    {value: 'totalRealMemory', name: 'Total Real Memory', yAxisTitle: 'Total Real Memory [GB]', scaleHidden: false},
    {value: 'freeRealMemory', name: 'Free Real Memory', yAxisTitle: 'Free Real Memory [GB]', scaleHidden: false},
    {value: 'usedRealMemory', name: 'Used Real Memory', yAxisTitle: 'Used Real Memory [GB]', scaleHidden: false},
    {value: 'totalVirtMemory', name: 'Total Virtual Memory', yAxisTitle: 'Total Virtual Memory [GB]', scaleHidden: false},
    {value: 'freeVirtMemory', name: 'Free Virtual Memory', yAxisTitle: 'Free Virtual Memory [GB]', scaleHidden: false},
    {value: 'usedVirtMemory', name: 'Used Virtual Memory', yAxisTitle: 'Used Virtual Memory [GB]', scaleHidden: false},
    {value: 'totalSwap', name: 'Total Swap', yAxisTitle: 'Total Swap [GB]', scaleHidden: false},
    {value: 'freeSwap', name: 'Free Swap', yAxisTitle: 'Free Swap [GB]', scaleHidden: false},
    {value: 'usedSwap', name: 'Used Swap', yAxisTitle: 'Used Swap [GB]', scaleHidden: false},
];
const scales = [
    {value: 0, name: 'None'},
    {value: 1024, name: 'KBytes'},
    {value: 1024 * 1024, name: 'MBytes'},
    {value: 1024 * 1024 * 1024, name: 'GBytes'}
];
const timeRanges = ['All', 'Today', 'This week', 'This month', 'This year', 'One week', 'One month', 'One year', 'Custom'];

class PerformanceChart extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            type: 'DAILY',
            metric: 'host.total.system.load',
            timeRange: 'Today',
            scale: 0,
            scaleHidden: true,
            customRangeHidden: true,
            nodeName: '',
            selectedAgent: {},
            yAxisTitle: 'System Load [%]',
            title: 'System Load',
            agents: [],
            startTime: moment().startOf('day').unix(),
            endTime: moment().endOf('day').unix(),
            limitStart: moment().startOf('day').toDate(),
            limitEnd: moment().endOf('day').toDate(),
            majorTickHours: 1,
            majorTickDays: 0,
            majorTickWeeks: 0,
            refreshLists: {},
        };
        this.now = new Date();
        this.handleTimeRange = this.handleTimeRange.bind(this);
        this.handleNodeName = this.handleNodeName.bind(this);
        this.handleMetric = this.handleMetric.bind(this);
        this.handleScale = this.handleScale.bind(this);
        this.handleCustomRangeStart = this.handleCustomRangeStart.bind(this);
        this.handleCustomRangeEnd = this.handleCustomRangeEnd.bind(this);
        this.getSource = this.getSource.bind(this);
    }

    componentDidMount() {
        getItem(process.env.REACT_APP_API_URL + 'agents?page=0&size=-1&sortBy=nodeName&sortDir=asc')
            .then((data) => {
                this.setState({agents: data._embedded.agentDtoes, selectedAgent: data._embedded.agentDtoes[0], nodeName: data._embedded.agentDtoes[0].nodeName})
            });
    }

    handleTimeRange(e) {
        switch (e.value) {
            case 'Today':
                this.setState({
                    type: 'DAILY',
                    timeRange: e.value,
                    customRangeHidden: true,
                    startTime: moment().startOf('day').unix(),
                    endTime: moment().endOf('day').unix(),
                    limitStart: moment().startOf('day').toDate(),
                    limitEnd: moment().endOf('day').toDate(),
                    majorTickHours: 1,
                    majorTickDays: 0,
                    majorTickWeeks: 0
                });
                break;
            case 'This week':
                this.setState({
                    type: 'WEEKLY',
                    timeRange: e.value,
                    customRangeHidden: true,
                    startTime: moment().startOf('week').unix(),
                    endTime: moment().endOf('week').unix(),
                    limitStart: moment().startOf('week').toDate(),
                    limitEnd: moment().endOf('week').toDate(),
                    majorTickHours: 0,
                    majorTickDays: 1,
                    majorTickWeeks: 0
                });
                break;
            case 'This month':
                this.setState({
                    type: 'MONTHLY',
                    timeRange: e.value,
                    customRangeHidden: true,
                    startTime: moment().startOf('month').unix(),
                    endTime: moment().endOf('month').unix(),
                    limitStart: moment().startOf('month').toDate(),
                    limitEnd: moment().endOf('month').toDate(),
                    majorTickHours: 0,
                    majorTickDays: 1,
                    majorTickWeeks: 0
                });
                break;
            case 'This year':
                this.setState({
                    type: 'YEARLY',
                    timeRange: e.value,
                    customRangeHidden: true,
                    startTime: moment().startOf('year').unix(),
                    endTime: moment().endOf('year').unix(),
                    limitStart: moment().startOf('year').toDate(),
                    limitEnd: moment().endOf('year').toDate(),
                    majorTickHours: 0,
                    majorTickDays: 0,
                    majorTickWeeks: 1
                });
                break;
            case 'One week':
                this.setState({
                    type: 'WEEKLY',
                    timeRange: e.value,
                    customRangeHidden: true,
                    startTime: moment().subtract(7, 'day').startOf('day').unix(),
                    endTime: moment().endOf('day').unix(),
                    limitStart: moment().subtract(7, 'day').startOf('day').toDate(),
                    limitEnd: moment().endOf('day').toDate(),
                    majorTickHours: 0,
                    majorTickDays: 1,
                    majorTickWeeks: 0
                });
                break;
            case 'One month':
                this.setState({
                    type: 'MONTHLY',
                    timeRange: e.value,
                    customRangeHidden: true,
                    startTime: moment().subtract(1, 'month').startOf('day').unix(),
                    endTime: moment().endOf('day').unix(),
                    limitStart: moment().subtract(1, 'month').startOf('day').toDate(),
                    limitEnd: moment().endOf('day').toDate(),
                    majorTickHours: 0,
                    majorTickDays: 1,
                    majorTickWeeks: 0
                });
                break;
            case 'One year':
                this.setState({
                    type: 'YEARLY',
                    timeRange: e.value,
                    customRangeHidden: true,
                    startTime: moment().subtract(1, 'year').startOf('day').unix(),
                    endTime: moment().endOf('day').unix(),
                    limitStart: moment().subtract(1, 'year').startOf('day').toDate(),
                    limitEnd: moment().endOf('day').toDate(),
                    majorTickHours: 0,
                    majorTickDays: 0,
                    majorTickWeeks: 1
                });
                break;
            case 'Custom':
                this.setState({
                    timeRange: e.value,
                    customRangeHidden: false
                });
                break;
            default:
                this.setState({
                    type: 'ALL',
                    timeRange: e.value,
                    startTime: moment().startOf('hour').unix(),
                    endTime: moment().endOf('hour').unix(),
                    limitStart: moment().startOf('hour').toDate(),
                    limitEnd: moment().endOf('hour').toDate(),
                    majorTickMinutes: 1,
                    majorTickHours: 0,
                    majorTickDays: 0,
                    majorTickWeeks: 0
                });
                break;
        }
    }

    handleNodeName(e) {
        this.setState({
            nodeName: e.value
        })
    }

    handleMetric(e) {
        this.setState({
            metric: e.selectedItem.value,
            yAxisTitle: e.selectedItem.yAxisTitle,
            title: e.selectedItem.name,
            scaleHidden: e.selectedItem.scaleHidden
        });
    }

    handleScale(e) {
        this.setState({
            scale: e.value
        })
    }

    handleCustomRangeStart(e) {
        this.setState({
            startTime: Math.trunc(e.value.getTime() / 1000)
        })
    }

    handleCustomRangeEnd(e) {
        this.setState({
            endTime: Math.trunc(e.value.getTime() / 1000)
        })
    }

    getSource(item) {
        if (this.state.metric === item.value) {
            return <Series key={item.value} valueField={'value'} name={item.name} width={2}>
                <Point size={4}/>
            </Series>;
        }
    }

    render() {
        return (
            <React.Fragment>
                <h2 className={'content-block'}>Performance</h2>
                <div className={'content-block'}>
                    <div className={'dx-card responsive-paddings'}>
                        <div className="options" style={{overflow: 'hidden'}}>
                            <div className="option" style={{float: 'left', marginRight: '20px'}}>
                                <span>Agent:</span>
                                <SelectBox items={this.state.agents} displayExpr='nodeName' valueExpr='nodeName'
                                           selectedItem={this.state.selectedAgent}
                                           value={this.state.nodeName}
                                           onValueChanged={this.handleNodeName}/>
                            </div>
                            <div className="option" style={{float: 'left', marginRight: '20px'}}>
                                <span>Metric:</span>
                                <SelectBox dataSource={sources} displayExpr='name' valueExpr='value' value={this.state.metric}
                                           onSelectionChanged={this.handleMetric}/>
                            </div>
                            <div className="option" hidden={this.state.scaleHidden} style={{float: 'left', marginRight: '20px'}}>
                                <span>Scale:</span>
                                <SelectBox dataSource={scales} displayExpr='name' valueExpr='value' value={this.state.scale} onValueChanged={this.handleScale}/>
                            </div>
                            <div className="option" style={{float: 'left', marginRight: '20px'}}>
                                <span>Time range:</span>
                                <SelectBox dataSource={timeRanges} value={this.state.timeRange} onValueChanged={this.handleTimeRange}/>
                            </div>
                            <div className="option" hidden={this.state.customRangeHidden}>
                                <span>Custom range:</span>
                                <DateBox defaultValue={this.now} type="datetime" onValueChanged={this.handleCustomRangeStart}/>
                                <DateBox defaultValue={this.now} type="datetime" onValueChanged={this.handleCustomRangeEnd}/>
                            </div>
                        </div>
                    </div>
                    <div className={'dx-card responsive-paddings'} style={{background: '#282828'}}>
                        <Chart
                            id="chart"
                            palette="Office"
                            dataSource={PerformanceDataSource(this.state.nodeName, this.state.type, this.state.metric, this.state.scale, this.state.startTime, this.state.endTime)}>
                            <CommonSeriesSettings argumentField="timestamp" type={'line'}/>
                            {sources.map(this.getSource)}
                            <ArgumentAxis argumentType="datetime" visualRange={{startValue: this.state.limitStart, endValue: this.state.limitEnd}}
                                          discreteAxisDivisionMode="crossLabels" valueMarginsEnabled={true}>
                                <Tick visible={true}/>
                                <Grid visible={true}/>
                                <TickInterval minutes={this.state.majorTickMinutes} hours={this.state.majorTickHours} days={this.state.majorTickDays}
                                              weeks={this.state.majorTickWeeks}/>
                            </ArgumentAxis>
                            <ValueAxis title={this.state.yAxisTitle}/>
                            <Legend verticalAlignment="bottom" horizontalAlignment="center" itemTextPosition="bottom"/>
                            <Export enabled={true}/>
                            <Title text={this.state.title + ': ' + this.state.nodeName} horizontalAlignment={'center'}/>
                            <Point hoverMode={'allArgumentPoints'}/>
                            <Crosshair enabled={true} width={2}>
                                <HorizontalLine dashStyle={'dot'} visible={true} width={2}>
                                    <Label visible={true} backgroundColor={'#949494'}/>
                                </HorizontalLine>
                                <VerticalLine dashStyle={'dot'} visible={true} width={2}>
                                    <Label visible={true} backgroundColor={'#949494'}/>
                                </VerticalLine>
                            </Crosshair>
                        </Chart>
                        <UpdateTimer/>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

export default PerformanceChart;