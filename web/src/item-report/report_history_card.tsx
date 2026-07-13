type Props = {
  item: string;
  action: string;
  status: string;
  icon: string;
};

function ReportHistoryCard({ item, action, status, icon }: Props) {
  return (
    <div className="lf-history-card">
      <div className="lf-history-image">{icon}</div>

      <div className="lf-history-info">
        <h3>{item}</h3>
        <p>{action}</p>
        <strong>{status}</strong>
      </div>

      <sl-button size="small" pill className="lf-sl-btn">
        View
      </sl-button>
    </div>
  );
}

export default ReportHistoryCard;