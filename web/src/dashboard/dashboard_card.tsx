type Props = {
  title: string;
  icon: string;
  className?: string;
};

function DashboardCard({ title, icon, className = "" }: Props) {
  return (
    <button className={`lf-action-card ${className}`}>
      <span>{title}</span>
      <div>{icon}</div>
    </button>
  );
}

export default DashboardCard;