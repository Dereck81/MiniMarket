import React from 'react';

interface SidebarLinkProps {
  onClick: () => void;
  icon: React.ReactNode;
  text: string;
  className?: string;
}

const SidebarLink: React.FC<SidebarLinkProps> = ({ onClick, icon, text, className }) => {
  return (
    <button
      onClick={onClick}
      className={`btn text-start text-white w-100 mb-2 d-flex align-items-center gap-2 ${className}`}
      style={{ backgroundColor: 'transparent', border: 'none' }}
    >
      {icon}
      <span>{text}</span>
    </button>
  );
};

export default SidebarLink;
